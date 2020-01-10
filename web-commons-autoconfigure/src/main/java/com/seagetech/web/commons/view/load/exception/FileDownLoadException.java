package com.seagetech.web.commons.view.load.exception;

import com.seagetech.common.exception.DefaultException;

/** 文件下载异常类
 * @author gdl
 * @date 2020/1/10 15:07
 * @company 矽甲（上海）信息科技有限公司
 */
public class FileDownLoadException extends RuntimeException {
    public FileDownLoadException() {
    }

    public FileDownLoadException(String message) {
        super(message);
    }
}
