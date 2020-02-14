package com.seagetech.web.commons.view.load.resolver;

import com.seagetech.web.commons.view.load.IFunctionInfo;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * @author wangzb
 * @date 2019/12/27 15:50
 * @company 矽甲（上海）信息科技有限公司
 */
public abstract class AbstractResolver<A extends Annotation,R extends IFunctionInfo> implements IResolver<A,R> {
    /**
     * 注解
     */
    protected A annotation;
    /**
     * 属性字段
     */
    protected Field field;
    /**
     * 当前的viewName
     */
    protected String viewName;

    /**
     * 初始化方法
     *
     * @param annotation 注解
     */
    @Override
    public void initialize(A annotation, Field field,String viewName) {
        this.annotation = annotation;
        this.field = field;
        this.viewName = viewName;
    }
}
