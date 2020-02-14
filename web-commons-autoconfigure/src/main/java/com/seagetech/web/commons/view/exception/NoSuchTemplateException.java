package com.seagetech.web.commons.view.exception;

import com.seagetech.web.exception.ParamVerifyException;

/**
 * @author wangzb
 * @date 2020/1/20 14:31
 * @company 矽甲（上海）信息科技有限公司
 */
public class NoSuchTemplateException extends ParamVerifyException {
    /**
     * 文件路径
     */
    private String filePath;

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     *
     * @param filePath
     * @param e
     */
    public NoSuchTemplateException(String filePath, Throwable e) {
        super(filePath + "不存在", e);
    }

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     *
     * @param filePath
     */
    public NoSuchTemplateException(String filePath) {
        super(filePath + "不存在");
    }

    public String getFilePath() {
        return filePath;
    }
}
