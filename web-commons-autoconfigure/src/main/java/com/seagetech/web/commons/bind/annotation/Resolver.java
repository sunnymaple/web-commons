package com.seagetech.web.commons.bind.annotation;

import com.seagetech.web.commons.bind.FunctionType;
import com.seagetech.web.commons.view.load.resolver.IResolver;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 标注注解解析器
 * @author wangzb
 * @date 2019/12/27 15:37
 * @company 矽甲（上海）信息科技有限公司
 */
@Documented
@Target({ ANNOTATION_TYPE })
@Retention(RUNTIME)
public @interface Resolver {
    /**
     * 注解解析器
     * @return
     */
    Class<? extends IResolver> resolverBy();

    /**
     * 类型
     * @return
     */
    FunctionType functionType();
}
