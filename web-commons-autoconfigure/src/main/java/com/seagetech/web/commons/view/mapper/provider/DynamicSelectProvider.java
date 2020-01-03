package com.seagetech.web.commons.view.mapper.provider;

import com.seagetech.common.util.SeageUtils;
import com.seagetech.web.commons.bind.Condition;
import com.seagetech.web.commons.bind.FunctionType;
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
     * and 连接符
     */
    private static final String AND = " AND ";

    /**
     * 创建动态sql查询语句
     * @param viewName 视图名称
     * @param params 查询条件参数
     * @return
     */
    public String createSql(String viewName,Map<String, Object> params){
        //sql
        StringBuilder sql = new StringBuilder(SELECT_SQL_PREFIX);

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
                    if (!SeageUtils.isEmpty(value)){
                        sql.append(AND)
                                .append(queryInfo.getColumnName())
                                .append(" ")
                                .append(queryInfo.getCondition().getCondition())
                                .append(" '")
                                .append(getValue(queryInfo.getCondition(),value))
                                .append("' ");
                    }
                })
            );
        }
        return sql.toString();
    }

    private String getValue(Condition condition,Object value){
        if (condition == Condition.LIKE){
            return "%" + value.toString() + "%";
        }
        return value.toString();
    }
}
