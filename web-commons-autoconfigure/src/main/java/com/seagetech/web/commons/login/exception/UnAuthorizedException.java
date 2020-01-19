package com.seagetech.web.commons.login.exception;

import com.seagetech.common.exception.DefaultException;
import com.seagetech.common.util.HttpStatusTypeEnum;

/**
 * 没有权限
 * @author wangzb
 * @date 2020/1/17 18:28
 * @company 矽甲（上海）信息科技有限公司
 */
public class UnAuthorizedException extends DefaultException {

    private static  HttpStatusTypeEnum httpStatusTypeEnum = HttpStatusTypeEnum.FORBIDDEN;
    /**
     * 用户主键
     */
    private Object userId;
    /**
     * 请求路径
     */
    private String path;

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public UnAuthorizedException(Object userId, String path,Throwable e) {
        super(httpStatusTypeEnum.getReasonPhrase(), e,httpStatusTypeEnum.value());
        this.userId = userId;
        this.path = path;
    }

    public Object getUserId() {
        return userId;
    }

    public String getPath() {
        return path;
    }
}
