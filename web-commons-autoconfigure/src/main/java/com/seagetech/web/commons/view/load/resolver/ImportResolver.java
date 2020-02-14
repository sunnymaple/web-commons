package com.seagetech.web.commons.view.load.resolver;

import com.seagetech.common.util.SeageUtils;
import com.seagetech.web.commons.bind.annotation.Import;
import com.seagetech.web.commons.view.config.Config;
import com.seagetech.web.commons.view.load.IOption;
import com.seagetech.web.commons.view.load.ImportInfo;
import com.seagetech.web.commons.view.load.exception.NotImplementsIOptionException;
import com.seagetech.web.commons.view.load.exception.OptionParamFormatException;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/** 导入功能注解解析器
 * @author gdl
 * @date 2020/1/9 9:57
 * @company 矽甲（上海）信息科技有限公司
 */
public class ImportResolver extends AbstractResolver<Import, ImportInfo> {
    @Override
    public List<ImportInfo> resolver() {
        ImportInfo importInfo = ImportInfo.builder().build()
                .setCol(annotation.col())
                .setColumnName(SeageUtils.isEmpty(annotation.columnName()) ?
                        SeageUtils.upperUnderScore(field.getName()) : annotation.columnName())
                .setFieldType(annotation.fieldType())
                .setName(SeageUtils.isEmpty(annotation.name()) ? field.getName() : annotation.name())
                .setDefaultValue(annotation.defaultValue());
        //选项
        Class<?> option = annotation.option();
        if (option != Void.class){
            boolean assignableFrom = IOption.class.isAssignableFrom(option);
            if (!assignableFrom){
                throw new NotImplementsIOptionException(option);
            }
            importInfo.setOption((Class<? extends IOption>) option);
            String[] optionParamsArray = annotation.optionParams();
            Map<String,Object> params = new HashMap<>(SeageUtils.initialCapacity());
            if (!SeageUtils.isEmpty(optionParamsArray)){
               for (String optionParam : optionParamsArray){
                   String[] optionParams = optionParam.split("=");
                   if (optionParams.length!=2){
                       throw new OptionParamFormatException(viewName,optionParam);
                   }
                   params.put(optionParams[0],optionParams[1]);
               }
            }
            importInfo.setOptionParams(params);
        }

        return Stream.of(importInfo).collect(Collectors.toList());
    }
}
