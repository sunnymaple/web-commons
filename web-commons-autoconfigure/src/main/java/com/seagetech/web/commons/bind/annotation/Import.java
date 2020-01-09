package com.seagetech.web.commons.bind.annotation;

import com.seagetech.web.commons.bind.FunctionType;
import com.seagetech.web.commons.view.load.resolver.DeleteResolver;
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
}
