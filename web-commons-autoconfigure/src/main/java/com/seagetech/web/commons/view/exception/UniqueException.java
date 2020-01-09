package com.seagetech.web.commons.view.exception;

import com.seagetech.web.exception.ParamVerifyException;

/**
 * @author wangzb
 * @date 2020/1/8 15:33
 * @company 矽甲（上海）信息科技有限公司
 */
public class UniqueException extends ParamVerifyException {
    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     *
     * @param message
     * @param e
     */
    public UniqueException(String message, Throwable e) {
        super(message, e);
    }

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     *
     * @param message
     */
    public UniqueException(String message) {
        super(message);
    }
}
