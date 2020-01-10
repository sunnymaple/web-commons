package com.seagetech.web.commons.bind.annotation;

import com.seagetech.web.commons.bind.FunctionType;
import com.seagetech.web.commons.view.load.resolver.UseLoginUserNameResolver;

import java.lang.annotation.*;

/**
 * 用户用户表标记可以用来登录的用户名
 * 一般为用户名、手机号、邮箱等唯一约束的字段
 * @author wangzb
 * @date 2020/1/9 13:59
 * @company 矽甲（上海）信息科技有限公司
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Resolver(resolverBy = UseLoginUserNameResolver.class,functionType = FunctionType.USE_LOGIN_USER_NAME)
public @interface UseLoginUserName {
    /**
     * 列名称
     * @return
     */
    String columnName() default "";
}
