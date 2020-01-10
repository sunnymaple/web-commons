package com.seagetech.web.commons.bind.annotation;

import com.seagetech.web.commons.bind.FunctionType;
import com.seagetech.web.commons.view.load.resolver.PrimaryKeyResolver;

import java.lang.annotation.*;

/**
 * 标识主键
 * @author wangzb
 * @date 2020/1/9 11:07
 * @company 矽甲（上海）信息科技有限公司
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Resolver(resolverBy = PrimaryKeyResolver.class,functionType = FunctionType.PRIMARY_KEY)
public @interface PrimaryKey {
    /**
     * 列名称
     * @return
     */
    String columnName() default "";
}
