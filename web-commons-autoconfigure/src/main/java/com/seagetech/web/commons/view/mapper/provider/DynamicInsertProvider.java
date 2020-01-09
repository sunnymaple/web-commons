package com.seagetech.web.commons.view.mapper.provider;

import com.seagetech.common.util.SeageUtils;
import com.seagetech.web.commons.view.mapper.def.DefaultValue;
import com.seagetech.web.commons.bind.FunctionType;
import com.seagetech.web.commons.view.load.AddInfo;
import com.seagetech.web.commons.view.load.IFunctionInfo;
import com.seagetech.web.commons.view.load.PageViewContainer;
import com.seagetech.web.commons.view.load.PageViewInfo;
import com.seagetech.web.commons.view.mapper.def.DefaultValueEnum;
import com.seagetech.web.commons.view.mapper.def.IDefaultValue;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 新增动态sql
 * @author wangzb
 * @date 2020/1/8 14:50
 * @company 矽甲（上海）信息科技有限公司
 */
public class DynamicInsertProvider {

    private static final String INSERT = "INSERT INTO ";
    private static final String LEFT_BRACKET = "(";
    private static final String RIGHT_BRACKET = ")";
    private static final String VALUES = " VALUES ";
    private static final String COMMA = " , ";
    private static final String RIGHT_MARK = "' ";
    private static final String LEFT_MARK = " '";


    /**
     * 创建sql
     * @param userId 当前操作人用户主键
     * @param viewName
     * @param params
     * @return
     */
    public String createSql(Object userId,String viewName, Map<String, Object> params) throws Exception {

        PageViewContainer pageViewContainer = PageViewContainer.getInstance();
        PageViewInfo pageViewInfo = pageViewContainer.get(viewName);

        StringBuilder insertSql = new StringBuilder(INSERT);
        insertSql.append(pageViewInfo.getTable()).append(LEFT_BRACKET);
        StringBuilder valuesSql = new StringBuilder(VALUES);
        valuesSql.append(LEFT_BRACKET);

        List<IFunctionInfo> functionInfos = pageViewInfo.get(FunctionType.ADD);
        for (int i=0;i<functionInfos.size();i++){
            AddInfo addInfo = (AddInfo) functionInfos.get(i);
            String columnName = addInfo.getColumnName();
            String name = addInfo.getName();
            Optional<Object> valueOp = Optional.ofNullable(params.get(name));
            if (!valueOp.isPresent()){
                //值为空，看是否有默认值
                String defaultValue = addInfo.getDefaultValue();
                if (!SeageUtils.isEmpty(defaultValue)){
                    Class<? extends IDefaultValue> defClass = DefaultValueEnum.getDefClass(defaultValue);
                    IDefaultValue iDefaultValue = defClass.newInstance();
                    valueOp = Optional.of(iDefaultValue.getDefaultValue(userId,name,defaultValue));
                }
            }
            final int temp = i;
            valueOp.ifPresent(value->{
                if (temp == 0){
                    insertSql.append(columnName);
                    valuesSql.append(LEFT_MARK).append(value).append(RIGHT_MARK);
                }else {
                    insertSql.append(COMMA).append(columnName);
                    valuesSql.append(COMMA).append(LEFT_MARK).append(value).append(RIGHT_MARK);
                }
            });
        }
        insertSql.append(RIGHT_BRACKET);
        valuesSql.append(RIGHT_BRACKET);
        return insertSql.append(valuesSql.toString()).toString();
    }

}
