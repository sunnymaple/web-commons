package com.seagetech.web.commons.view.load.resolver;

import com.seagetech.common.util.SeageUtils;
import com.seagetech.web.commons.bind.annotation.Import;
import com.seagetech.web.commons.view.load.ImportInfo;

import java.util.List;
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
                .setDefaultValue(annotation.defaultValue())
                .setOption(annotation.option());
        return Stream.of(importInfo).collect(Collectors.toList());
    }
}
