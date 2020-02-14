package com.seagetech.web.commons.bind.annotation;

import com.seagetech.web.commons.bind.FunctionType;
import com.seagetech.web.commons.view.load.IOption;
import com.seagetech.web.commons.view.load.resolver.ImportResolver;

import java.io.Serializable;
import java.lang.annotation.*;

/** 导入注解
 * @author gdl
 * @date 2020/1/8 14:57
 * @company 矽甲（上海）信息科技有限公司
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Resolver(resolverBy = ImportResolver.class,functionType = FunctionType.IMPORT)
public @interface Import {
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
     * 第几列 从 0 开始 必须加，不加无法知道第几列
     * @return
     */
    int col() default 0;
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
     * 选项
     * 必须实现接口{@link IOption}
     * @return
     */
    Class<?> option() default Void.class;

    /**
     * 获取option选项的参数
     * 形式:key:value
     * @return
     */
    String[] optionParams() default {};
}
