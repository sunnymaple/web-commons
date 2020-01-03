package com.seagetech.web.commons.view.exception;

/**
 * 未开启功能
 * @author wangzb
 * @date 2019/12/31 9:50
 * @company 矽甲（上海）信息科技有限公司
 */
public class DisabledFunctionException extends RuntimeException{
    /**
     * 功能
     */
    private Object functionType;

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param functionType 功能
     */
    public DisabledFunctionException(Object functionType) {
        super("未开启功能" + functionType + "或者无效的功能！");
        this.functionType = functionType;
    }
}
