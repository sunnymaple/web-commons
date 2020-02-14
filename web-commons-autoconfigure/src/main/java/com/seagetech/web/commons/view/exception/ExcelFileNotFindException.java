package com.seagetech.web.commons.view.exception;

import com.seagetech.web.exception.ParamVerifyException;

/**
 * excel文件为空异常
 * @author wangzb
 * @date 2020/1/20 10:22
 * @company 矽甲（上海）信息科技有限公司
 */
public class ExcelFileNotFindException extends ParamVerifyException {

    private static final String MESSAGE = "请上传文件";

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     *
     * @param e
     */
    public ExcelFileNotFindException(Throwable e) {
        super(MESSAGE, e);
    }

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     *
     */
    public ExcelFileNotFindException() {
        super(MESSAGE);
    }


}
