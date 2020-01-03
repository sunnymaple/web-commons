package com.seagetech.web.commons.bind.annotation;

import com.seagetech.web.commons.bind.FunctionType;
import com.seagetech.web.commons.view.load.resolver.QueriesResolver;

import java.lang.annotation.*;

/**
 * 注解{@link Query}的复合注解
 * @author wangzb
 * @date 2019/12/30 15:23
 * @company 矽甲（上海）信息科技有限公司
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Resolver(resolverBy = QueriesResolver.class,functionType = FunctionType.QUERY)
public @interface Queries {
    /**
     * 指定多个Query
     * @return
     */
    Query[] value();
}
