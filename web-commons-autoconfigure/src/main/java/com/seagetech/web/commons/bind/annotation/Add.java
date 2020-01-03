package com.seagetech.web.commons.bind.annotation;

import java.io.Serializable;
import java.lang.annotation.*;
import java.util.Date;

/**
 * 添加功能组件
 * @author wangzb
 * @date 2019/12/31 9:43
 * @company 矽甲（上海）信息科技有限公司
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Add {
    /**
     * 字段类型
     * @return
     */
    Class<? extends Serializable> fieldType() default String.class;

    /**
     * 如果是日期格式{@link Date} ,请指定日期格式，如yyyy-MM-dd HH:mm:ss
     * 如果是字符串或者其他数字类型，可以指定正则表达式
     * @return
     */
    String format() default "";

    /**
     * 除了使用正则表达式
     * @return
     */
    Class<? extends Annotation>[] validateds() default {};

    /**
     * 字段名称
     * @return
     */
    String fieldName() default "";
}
