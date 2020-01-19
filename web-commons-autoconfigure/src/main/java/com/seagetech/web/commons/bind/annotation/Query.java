package com.seagetech.web.commons.bind.annotation;

import com.seagetech.web.commons.bind.Condition;
import com.seagetech.web.commons.bind.FunctionType;
import com.seagetech.web.commons.bind.SortType;
import com.seagetech.web.commons.view.load.resolver.QueryResolver;

import java.lang.annotation.*;

/**
 * 列表查询参数定义
 * @author wangzb
 * @date 2019/12/26 13:42
 * @company 矽甲（上海）信息科技有限公司
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Repeatable(Queries.class)
@Resolver(resolverBy = QueryResolver.class,functionType = FunctionType.QUERY)
public @interface Query {

    /**
     * 条件,默认为等于
     * @return
     */
    Condition condition() default Condition.EQ;

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

    /**
     * 只查询的值
     * 比如只查询出状态为1的，0表示被删除的不显示出来
     * @return
     */
    String[] queryOnly() default {};

    /**
     * 不查询出的值
     * @return
     */
    String[] notQuery() default {};

    /**
     * 排序
     *
     * @return
     */
    SortType sort() default SortType.NONE;
}
