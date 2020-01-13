package com.seagetech.web.commons.view.load.resolver;

import com.seagetech.common.util.SeageUtils;
import com.seagetech.web.commons.bind.annotation.Export;
import com.seagetech.web.commons.bind.annotation.Import;
import com.seagetech.web.commons.view.load.ExportInfo;
import com.seagetech.web.commons.view.load.ImportInfo;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/** 导入功能注解解析器
 * @author gdl
 * @date 2020/1/9 9:57
 * @company 矽甲（上海）信息科技有限公司
 */
public class ExportResolver extends AbstractResolver<Export, ExportInfo> {
    @Override
    public List<ExportInfo> resolver() {
        ExportInfo importInfo = ExportInfo.builder().build()
                .setCol(annotation.col())
                .setColumnName(SeageUtils.isEmpty(annotation.columnName()) ?
                        SeageUtils.upperUnderScore(field.getName()) : annotation.columnName())
                .setDefaultValue(annotation.defaultValue())
                .setHeadName(annotation.headName())
                .setName(SeageUtils.isEmpty(annotation.name()) ? field.getName() : annotation.name());
        return Stream.of(importInfo).collect(Collectors.toList());
    }
}
