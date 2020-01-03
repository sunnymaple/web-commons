package com.seagetech.web.commons.view.load.resolver;

import com.seagetech.common.util.SeageUtils;
import com.seagetech.web.commons.bind.annotation.Queries;
import com.seagetech.web.commons.bind.annotation.Query;
import com.seagetech.web.commons.view.load.QueryInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询条件解析器，注解{@link Query}的复合注解
 * @author wangzb
 * @date 2019/12/30 15:52
 * @company 矽甲（上海）信息科技有限公司
 */
public class QueriesResolver extends AbstractResolver<Queries, QueryInfo>{
    /**
     * 解析
     *
     * @return R
     */
    @Override
    public List<QueryInfo> resolver() {
        Query[] values = annotation.value();
        List<QueryInfo> results = new ArrayList<>(values.length);
        for (Query query : values){
            String fieldName = field.getName();
            //参数名称，为指定时使用列名
            String name = SeageUtils.isEmpty(query.name()) ? fieldName : query.name();
            //列名称
            String columnName = SeageUtils.isEmpty(query.columnName()) ?
                    SeageUtils.upperUnderScore(fieldName) : query.columnName();
            results.add(new QueryInfo(query.condition(),name,columnName));
        }
        return results;
    }
}
