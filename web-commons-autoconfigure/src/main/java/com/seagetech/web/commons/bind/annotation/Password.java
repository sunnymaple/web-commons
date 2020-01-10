package com.seagetech.web.commons.bind.annotation;

import com.seagetech.web.commons.bind.FunctionType;
import com.seagetech.web.commons.view.load.resolver.PasswordResolver;

import java.lang.annotation.*;

/**
 * 指定密码字段 用于登录
 * @author wangzb
 * @date 2020/1/9 11:57
 * @company 矽甲（上海）信息科技有限公司
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Resolver(resolverBy = PasswordResolver.class,functionType = FunctionType.PASSWORD)
public @interface Password {
    /**
     * 列名称
     * @return
     */
    String columnName() default "";
}
