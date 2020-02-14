package com.seagetech.web.commons.view.exception;

import com.seagetech.web.exception.ParamVerifyException;

/**
 * @author wangzb
 * @date 2020/1/20 10:32
 * @company 矽甲（上海）信息科技有限公司
 */
public class ExcelFileTypeException extends ParamVerifyException {
    /**
     * 文件名称
     */
    private String fileName;

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     *
     * @param fileName 文件名称
     * @param e
     */
    public ExcelFileTypeException(String fileName, Throwable e) {
        super(fileName + "不是一个正确的excel文件", e);
        this.fileName = fileName;
    }

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     *
     * @param fileName
     */
    public ExcelFileTypeException(String fileName) {
        super(fileName + "不是一个正确的excel文件");
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
