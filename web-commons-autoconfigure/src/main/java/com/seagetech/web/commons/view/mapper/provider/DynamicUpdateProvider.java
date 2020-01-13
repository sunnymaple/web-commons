package com.seagetech.web.commons.view.mapper.provider;

import com.seagetech.common.util.SeageUtils;
import com.seagetech.web.commons.bind.FunctionType;
import com.seagetech.web.commons.view.load.*;
import com.seagetech.web.commons.view.mapper.def.DefaultValueEnum;
import com.seagetech.web.commons.view.mapper.def.IDefaultValue;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 修改动态sql
 * @author wangzb
 * @date 2020/1/13 14:27
 * @company 矽甲（上海）信息科技有限公司
 */
public class DynamicUpdateProvider {

    private static final String UPDATE = "UPDATE ";

    private static final String SET = " SET ";
    private static final String WHERE = " WHERE ";
    private static final String EQ = " = ";
    private static final String RIGHT_MARK = "' ";
    private static final String LEFT_MARK = " '";
    private static final String COMMA = " , ";


    public String createSql(Object userId,String viewName, Map<String, Object> params) throws Exception {

        PageViewContainer pageViewContainer = PageViewContainer.getInstance();
        PageViewInfo pageViewInfo = pageViewContainer.get(viewName);

        StringBuilder sql = new StringBuilder(UPDATE);
        sql.append(pageViewInfo.getTable()).append(SET);

        List<IFunctionInfo> functionInfos = pageViewInfo.get(FunctionType.ADD);
        for (int i=0;i<functionInfos.size();i++){
            AddInfo addInfo = (AddInfo) functionInfos.get(i);
            String columnName = addInfo.getColumnName();
            String name = addInfo.getName();
            Optional<Object> valueOp = Optional.ofNullable(params.get(name));
            final int temp = i;
            valueOp.ifPresent(value->{
                if (temp != 0){
                    sql.append(COMMA);
                }
                sql.append(columnName).append(EQ).append(LEFT_MARK).append(value).append(RIGHT_MARK);
            });
        }
        PrimaryKeyInfo primaryKeyInfo = pageViewInfo.getPrimaryKey();
        sql.append(WHERE)
                .append(primaryKeyInfo.getColumnName())
                .append(EQ)
                .append(LEFT_MARK)
                .append(params.get(primaryKeyInfo.getName()))
                .append(RIGHT_MARK);
        return sql.toString();
    }
}
