package com.seagetech.web.commons.bind.annotation;

import com.seagetech.web.commons.bind.FunctionType;
import com.seagetech.web.commons.view.load.resolver.AddResolver;

import java.io.Serializable;
import java.lang.annotation.*;

/**
 * 添加功能组件
 * 字段的验证支持
 * @author wangzb
 * @date 2019/12/31 9:43
 * @company 矽甲（上海）信息科技有限公司
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Resolver(resolverBy = AddResolver.class,functionType = FunctionType.ADD)
public @interface Add {
    /**
     * 字段类型
     * @return
     */
    Class<? extends Serializable> fieldType() default String.class;

    /**
     * 参数名称，默认为属性名
     * @return
     */
    String name() default "";

    /**
     * 数据库列名称，默认为属性"_"形式
     * @return
     */
    String columnName() default "";

    /**
     * 页面查询条件的label的名称
     * 可以为空，有前端或者客户端自定义
     * 如果要基于该框架实现一个后台模板，可以指定label的值
     * @return
     */
    String label() default "";
}
