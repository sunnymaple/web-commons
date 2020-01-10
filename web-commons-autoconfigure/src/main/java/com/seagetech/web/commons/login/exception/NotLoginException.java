package com.seagetech.web.commons.login.exception;

import com.seagetech.common.exception.DefaultException;
import com.seagetech.common.util.HttpStatusTypeEnum;

/**
 * 未登录或者登录超时异常
 * @author wangzb
 * @date 2020/1/10 10:46
 * @company 矽甲（上海）信息科技有限公司
 */
public class NotLoginException extends DefaultException {
    /**
     * 未登录或者登录超时
     */
    private static final HttpStatusTypeEnum httpStatus = HttpStatusTypeEnum.UNAUTHORIZED;

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public NotLoginException() {
        super(httpStatus.getReasonPhrase(),httpStatus.value());
    }
}
