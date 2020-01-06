package com.seagetech.web.commons.bind.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 操作日志注解
 * @author gdl
 * @date 2020/1/6 10:47
 * @company 矽甲（上海）信息科技有限公司
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface OperateLog {
    /**
     * 操作类型
     */
    int operateType() default 0;

    /**
     * class类
     */
    Class<? extends IOperateLog> objClass();

    /**
     * 备注信息
     */
    String detail() default "";

}
