package com.seagetech.web.commons.bind.annotation;

import com.seagetech.web.commons.bind.FunctionType;
import com.seagetech.web.commons.view.load.IPageViewCustom;

import java.lang.annotation.*;

/**
 * 定义一个页面视图
 * @author wangzb
 * @date 2019/12/19 13:21
 * @company 矽甲（上海）信息科技有限公司
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PageView {
    /**
     * 视图名称，即viewName
     * @return
     */
    String value();

    /**
     * 视图路径
     * @return
     */
    String viewPath() default "";

    /**
     * 表名称，默认为实体类下划线的形式
     * @return
     */
    String table() default "";

    /**
     * 如果列表查询视图内容，需要指定视图名称
     * @return
     */
    String view() default "";

    /**
     * 主键
     * @return
     */
    String tableId();

    /**
     * 开启自定义的功能
     * @return
     */
    FunctionType[] enableCustomFunctions() default {};

    /**
     * 自定查询的class
     * 1、该class必须有实现类，且受springIOC容器管理
     * 2、该class必须实现{@link IPageViewCustom}
     * @return
     */
    String customClass() default "";

    /**
     * excel从第几行开始读取
     * @return
     */
    int row() default 1;

    /**
     * excel文件路径
     * 相对路径
     * @return
     */
    String excelTemplatePath() default "";
}
