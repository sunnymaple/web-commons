package com.seagetech.web.commons.login.exception;

import com.seagetech.common.exception.DefaultException;
import com.seagetech.common.util.HttpStatusTypeEnum;
import com.seagetech.common.util.SeageUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * 登录异常
 * @author wangzb
 * @date 2020/1/9 14:51
 * @company 矽甲（上海）信息科技有限公司
 */
@Slf4j
public class LoginException extends DefaultException {

    public static final String DEFAULT_MESSAGE = "用户名或密码错误！";

    /**
     * 登录异常
     */
    protected static final HttpStatusTypeEnum httpStatusType = HttpStatusTypeEnum.FORBIDDEN;
    /**
     * 用于登录的用户名
     */
    private String userName;

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     *
     * @param userName 用户名
     */
    public LoginException(String userName) {
        super(DEFAULT_MESSAGE, httpStatusType.value());
        this.userName = userName;
    }

    /**
     *
     * @since 1.4
     */
    public LoginException(String message, String userName) {
        super(message, httpStatusType.value());
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    /**
     * Returns the detail message string of this throwable.
     *
     * @return the detail message string of this {@code Throwable} instance
     * (which may be {@code null}).
     */
    @Override
    public String getMessage() {
        log.info("use userName:" + userName);
        return super.getMessage();
    }
}
