package com.seagetech.web.commons.bind.annotation;

import com.seagetech.web.commons.bind.FunctionType;
import com.seagetech.web.commons.view.load.resolver.UserStatusResolver;
import com.seagetech.web.commons.view.load.resolver.UserStatusResolverList;

import java.lang.annotation.*;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 用户状态标记
 * @author wangzb
 * @date 2020/1/9 14:29
 * @company 矽甲（上海）信息科技有限公司
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Repeatable(UserStatus.List.class)
@Resolver(resolverBy = UserStatusResolver.class,functionType = FunctionType.USER_STATUS)
public @interface UserStatus {

    /**
     * 状态值
     * @return
     */
    String value();

    /**
     * 状态是否有效，即正常状态，可以登录
     * @return
     */
    boolean valid() default false;

    /**
     * 非正常状态指定抛出的信息
     * @return
     */
    String message() default "";

    @Target({ElementType.FIELD})
    @Retention(RUNTIME)
    @Documented
    @Resolver(resolverBy = UserStatusResolverList.class,functionType = FunctionType.USER_STATUS)
    @interface List {
        UserStatus[] value();
    }
}
