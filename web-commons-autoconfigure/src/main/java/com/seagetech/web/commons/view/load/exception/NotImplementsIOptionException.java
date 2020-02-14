package com.seagetech.web.commons.view.load.exception;

import com.seagetech.web.commons.view.load.IOption;

/**
 * 没有实现IOption异常
 * @author wangzb
 * @date 2020/1/19 17:18
 * @company 矽甲（上海）信息科技有限公司
 */
public class NotImplementsIOptionException extends PageViewException{

    private Class<?> aClass;

    public NotImplementsIOptionException(Class<?> aClass) {
        super(aClass.getName() + " 没有实现 " + IOption.class.getName());
        this.aClass = aClass;
    }

    public Class<?> getaClass() {
        return aClass;
    }
}
