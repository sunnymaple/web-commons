package com.seagetech.web.commons.bind.annotation;

import com.seagetech.web.commons.bind.FunctionType;
import com.seagetech.web.commons.bind.OptionType;
import com.seagetech.web.commons.view.load.resolver.OptionResolver;

import java.lang.annotation.*;

/**
 * 定义下拉选项
 * @author wangzb
 * @date 2020/1/19 15:02
 * @company 矽甲（上海）信息科技有限公司
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Resolver(resolverBy = OptionResolver.class,functionType = FunctionType.OPTION)
public @interface Option {

    /**
     * 类型
     * {@link OptionType}
     * @return
     */
    OptionType value();

}
