package com.seagetech.web.commons.bind.annotation;

import com.seagetech.web.commons.bind.FunctionType;
import com.seagetech.web.commons.view.load.resolver.AddResolver;
import com.seagetech.web.commons.view.load.resolver.DeleteResolver;

import java.lang.annotation.*;

/**
 * @author gdl
 * @date 2020/1/6 16:45
 * @company 矽甲（上海）信息科技有限公司
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Resolver(resolverBy = DeleteResolver.class,functionType = FunctionType.DELETE)
public @interface Delete {
    /**
     * 数据库列名称，默认为属性"_"形式  主键
     * @return
     */
    String columnName() default "";
    /**
     * 数据库列名称，默认为属性"_"形式  状态
     * @return
     */
    String statusName() default "status";

    /**
     * 删除类型 物理删除
     * @return
     */
    int deleteType() default 0;
}
