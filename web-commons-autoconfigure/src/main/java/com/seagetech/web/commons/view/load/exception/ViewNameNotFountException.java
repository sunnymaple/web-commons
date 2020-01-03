package com.seagetech.web.commons.view.load.exception;

import com.seagetech.common.exception.DefaultException;
import com.seagetech.common.util.HttpStatusTypeEnum;

/**
 * @author wangzb
 * @date 2019/12/23 17:22
 * @company 矽甲（上海）信息科技有限公司
 */
public class ViewNameNotFountException extends DefaultException {

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     * @param viewName 视图名称
     */
    public ViewNameNotFountException(String viewName) {
        super("页面" + viewName + "不存在！",HttpStatusTypeEnum.NOT_FOUND.value());
    }
}
