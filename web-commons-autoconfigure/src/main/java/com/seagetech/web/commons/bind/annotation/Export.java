package com.seagetech.web.commons.bind.annotation;

import com.seagetech.web.commons.bind.FunctionType;
import com.seagetech.web.commons.view.load.ExportInfo;
import com.seagetech.web.commons.view.load.resolver.ExportResolver;

import java.lang.annotation.*;

/** 导出接口注解
 * @author gdl
 * @date 2020/1/13 9:06
 * @company 矽甲（上海）信息科技有限公司
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Resolver(resolverBy = ExportResolver.class,functionType = FunctionType.EXPORT)
public @interface Export {
    /**
     * 表头名称
     * @return
     */
    String headName() default "";
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
}
