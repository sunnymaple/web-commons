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
     * 唯一性约束，默认为false
     * 如果指定为true，会先判断是否有该值
     * @return
     */
    boolean unique() default false;

    /**
     * 默认值
     * 可以指定具体的值，如1,2这样的数字，或者指定一个字符串："张三"、"abc"
     * 同时支持特定的参数：
     * 1、当前系统时间：#date|yyyy-MM-dd HH:mm:ss
     *     其中“#date|”为固定格式，"|"后面为日期格式
     * 2、当前操作用户：#user
     * @return
     */
    String defaultValue() default "";

    /**
     * 页面查询条件的label的名称
     * 可以为空，有前端或者客户端自定义
     * 如果要基于该框架实现一个后台模板，可以指定label的值
     * @return
     */
    String label() default "";
}
