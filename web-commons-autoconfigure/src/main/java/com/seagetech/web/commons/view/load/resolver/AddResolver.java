package com.seagetech.web.commons.view.load.resolver;

import com.seagetech.common.util.SeageUtils;
import com.seagetech.web.commons.bind.annotation.Add;
import com.seagetech.web.commons.view.load.AddInfo;

import javax.validation.Constraint;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 新增功能注解解析器
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
        List<Annotation> validates = new ArrayList<>();
        Arrays.stream(field.getAnnotations()).forEach(annotation->{
            Constraint constraint = annotation.annotationType().getAnnotation(Constraint.class);
            if (constraint != null){
                validates.add(annotation);
            }
        });
        addInfo.setValidates(validates);
        return addInfos;
    }
}
