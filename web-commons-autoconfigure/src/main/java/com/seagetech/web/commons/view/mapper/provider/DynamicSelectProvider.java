package com.seagetech.web.commons.view.mapper.provider;

import com.seagetech.common.util.SeageUtils;
import com.seagetech.web.commons.bind.Condition;
import com.seagetech.web.commons.bind.FunctionType;
import com.seagetech.web.commons.bind.SortType;
import com.seagetech.web.commons.view.load.IFunctionInfo;
import com.seagetech.web.commons.view.load.PageViewContainer;
import com.seagetech.web.commons.view.load.PageViewInfo;
import com.seagetech.web.commons.view.load.QueryInfo;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 查询动态sql
 * @author wangzb
 * @date 2019/12/26 10:45
 * @company 矽甲（上海）信息科技有限公司
 */
public class DynamicSelectProvider {
    /**
     * 查询sql前缀
     */
    private static final String SELECT_SQL_PREFIX = "SELECT * FROM ";
    /**
     * 查询语句 条件where
     */
    private static final String SELECT_SQL_WHERE_PREFIX = " WHERE 1=1 ";
    /**
     * 查询语句 排序
     */
    private static final String SELECT_SQL_ORDER_BY_PREFIX = " ORDER BY ";
    /**
     * and 连接符
     */
    private static final String AND = " AND ";
    private static final String IN = " IN ";
    private static final String NOT_IN = " NOT IN ";

    /**
     * 创建动态sql查询语句
     * @param viewName 视图名称
     * @param params 查询条件参数
     * @return
     */
    public String createSql(String viewName,Map<String, Object> params){
        //sql
        StringBuilder sql = new StringBuilder(SELECT_SQL_PREFIX);

        //order by
        StringBuilder orderBySql = new StringBuilder();
        PageViewContainer pageViewContainer = PageViewContainer.getInstance();
        PageViewInfo pageViewInfo = pageViewContainer.get(viewName);
        //表
        String view = pageViewInfo.getView();
        String tableName = SeageUtils.isEmpty(view) ? pageViewInfo.getTable() : view;
        sql.append(tableName);
        //查询条件
        if (params != null && params.size()>0){
            sql.append(SELECT_SQL_WHERE_PREFIX);
            Optional<List<IFunctionInfo>> iInfosOp = Optional.ofNullable(pageViewInfo.get(FunctionType.QUERY));
            iInfosOp.ifPresent(iInfos ->
                iInfos.forEach(iInfo -> {
                    QueryInfo queryInfo = (QueryInfo) iInfo;
                    String name = queryInfo.getName();
                    Object value = params.get(name);
                    String columnName = queryInfo.getColumnName();
                    if (!SeageUtils.isEmpty(value)){
                        sql.append(AND)
                                .append(queryInfo.getColumnName())
                                .append(" ")
                                .append(queryInfo.getCondition().getCondition())
                                .append(" '")
                                .append(getValue(queryInfo.getCondition(),value))
                                .append("' ");
                    }else {
                        //固定条件
                        String[] queryOnlys = queryInfo.getQueryOnly();
                        appendRigidCondition(sql,queryOnlys,columnName,IN);
                        String[] notQuerys = queryInfo.getNotQuery();
                        appendRigidCondition(sql,notQuerys,columnName,NOT_IN);
                    }
                    //sort
                    SortType sortType = queryInfo.getSortType();
                    if (sortType!=SortType.NONE){
                        if (!SeageUtils.isEmpty(orderBySql.toString())){
                            orderBySql.append(",");
                        }
                        orderBySql.append(columnName).append(" ").append(sortType.getSort());
                    }
                })
            );
        }
        if (!SeageUtils.isEmpty(orderBySql.toString())){
            sql.append(SELECT_SQL_ORDER_BY_PREFIX).append(orderBySql);
        }
        return sql.toString();
    }

    /**
     * 拼接固定条件
     * @param sql
     * @param rigidConditions
     * @param columnName
     * @param condition
     */
    private void appendRigidCondition(StringBuilder sql,String[] rigidConditions,String columnName,String condition){
        if (!SeageUtils.isEmpty(rigidConditions)){
            sql.append(AND).append(columnName).append(" ").append(condition).append("(");
            for (int i=0;i<rigidConditions.length;i++){
                String queryOnly = rigidConditions[i];
                if (i!=0){
                    sql.append(",");
                }
                sql.append(" '").append(queryOnly).append("' ");
            }
            sql.append(")");
        }
    }

    private String getValue(Condition condition,Object value){
        if (condition == Condition.LIKE){
            return "%" + value.toString() + "%";
        }
        return value.toString();
    }
}
