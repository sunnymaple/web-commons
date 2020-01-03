package com.seagetech.web.commons.view.exception;

import com.seagetech.web.commons.view.load.IPageViewCustom;

/**
 * 为实现{@link IPageViewCustom}接口异常
 * @author wangzb
 * @date 2019/12/30 14:00
 * @company 矽甲（上海）信息科技有限公司
 */
public class NotImplementsPageViewCustomException extends RuntimeException{
    /**
     * 类全名
     */
    private String className;

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public NotImplementsPageViewCustomException(String className) {
        super(className + "未实现com.seagetech.web.commons.view.load.IPageViewCustom接口");
        this.className = className;
    }

    public String getClassName() {
        return className;
    }
}
