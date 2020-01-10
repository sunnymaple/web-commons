package com.seagetech.web.commons.view.load.resolver;

import com.seagetech.common.util.SeageUtils;
import com.seagetech.web.commons.bind.annotation.PrimaryKey;
import com.seagetech.web.commons.view.load.PrimaryKeyInfo;

import java.util.Arrays;
import java.util.List;

/**
 * 主键解析
 * @author wangzb
 * @date 2020/1/9 11:34
 * @company 矽甲（上海）信息科技有限公司
 */
public class PrimaryKeyResolver extends AbstractResolver<PrimaryKey, PrimaryKeyInfo>{

    /**
     * 解析
     *
     * @return R
     */
    @Override
    public List<PrimaryKeyInfo> resolver() {
        //字段名称，为指定时使用列名
        String fieldName = field.getName();
        //列名称
        String columnName = SeageUtils.isEmpty(annotation.columnName()) ?
                SeageUtils.upperUnderScore(fieldName) : annotation.columnName();
        return Arrays.asList(new PrimaryKeyInfo(fieldName,columnName));
    }
}
