package com.seagetech.web.commons.view.load.resolver;

import com.seagetech.common.util.SeageUtils;
import com.seagetech.web.commons.bind.annotation.Add;
import com.seagetech.web.commons.view.load.AddInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangzb
 * @date 2020/1/6 15:11
 * @company 矽甲（上海）信息科技有限公司
 */
public class AddResolver extends AbstractResolver<Add, AddInfo>{
    /**
     * 解析
     *
     * @return R
     */
    @Override
    public List<AddInfo> resolver() {
        List<AddInfo> addInfos = new ArrayList<>(1);
        String fieldName = field.getName();
        //参数名称，为指定时使用列名
        String name = SeageUtils.isEmpty(annotation.name()) ? fieldName : annotation.name();
        //列名称
        String columnName = SeageUtils.isEmpty(annotation.columnName()) ?
                SeageUtils.upperUnderScore(fieldName) : annotation.columnName();

        AddInfo addInfo = AddInfo.builder().build()
                .setName(name)
                .setColumnName(columnName)
                .setFieldType(annotation.fieldType());
        addInfos.add(addInfo);
        //查找验证
//        Annotation[] annotations = field.getAnnotations();

        return addInfos;
    }
}
