package com.seagetech.web.commons.view.load.resolver;

import com.seagetech.common.util.SeageUtils;
import com.seagetech.web.commons.bind.annotation.Query;
import com.seagetech.web.commons.view.load.QueryInfo;

import java.util.Arrays;
import java.util.List;

/**
 * 查询条件解析器
 * @author wangzb
 * @date 2019/12/27 15:49
 * @company 矽甲（上海）信息科技有限公司
 */
public class QueryResolver extends AbstractResolver<Query, QueryInfo>{
    /**
     * 解析
     *
     * @return {@link QueryInfo}
     */
    @Override
    public List<QueryInfo> resolver() {
        String fieldName = field.getName();
        //参数名称，为指定时使用列名
        String name = SeageUtils.isEmpty(annotation.name()) ? fieldName : annotation.name();
        //列名称
        String columnName = SeageUtils.isEmpty(annotation.columnName()) ?
                SeageUtils.upperUnderScore(fieldName) : annotation.columnName();
        return Arrays.asList(new QueryInfo(annotation.condition(),name,columnName,annotation.label(),
                annotation.queryOnly(),annotation.notQuery(),annotation.sort()));
    }
}
