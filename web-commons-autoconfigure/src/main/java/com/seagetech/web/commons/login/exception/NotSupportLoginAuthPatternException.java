package com.seagetech.web.commons.login.exception;

/**
 * 不支持的登录模式异常
 * @author wangzb
 * @date 2020/1/2 17:34
 * @company 矽甲（上海）信息科技有限公司
 */
public class NotSupportLoginAuthPatternException extends RuntimeException{
    /**
     * 模式
     */
    private String pattern;

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public NotSupportLoginAuthPatternException(String pattern) {
        super("不支持" + pattern + "登录认证模式");
        this.pattern = pattern;
    }

    public String getPattern() {
        return pattern;
    }
}
