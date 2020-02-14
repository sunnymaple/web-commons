package com.seagetech.web.commons.view.load.resolver;

import com.seagetech.web.commons.view.load.IFunctionInfo;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

/**
 * 注解解析器
 * @author wangzb
 * @date 2019/12/27 15:38
 * @company 矽甲（上海）信息科技有限公司
 */
public interface IResolver<A extends Annotation,R extends IFunctionInfo> {
    /**
     * 初始化方法
     * @param annotation 注解
     * @param field 字段
     * @param viewName 当前的viewName
     */
    void initialize(A annotation, Field field,String viewName);

    /**
     * 解析
     * @return R
     */
    List<R> resolver();
}
