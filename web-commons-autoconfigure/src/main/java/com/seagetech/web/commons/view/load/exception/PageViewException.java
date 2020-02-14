package com.seagetech.web.commons.view.load.exception;

/**
 * PageView异常
 * @author wangzb
 * @date 2019/12/23 17:21
 * @company 矽甲（上海）信息科技有限公司
 */
public class PageViewException extends RuntimeException{
    /**
     * viewName 视图名称
     */
    private String viewName;

    public PageViewException() {
    }

    public PageViewException(String message) {
        super(message);
    }

    public PageViewException(String message, Throwable cause) {
        super(message, cause);
    }

    public PageViewException(Throwable cause) {
        super(cause);
    }

    public PageViewException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public PageViewException(String viewName,String message) {
        super(viewName + ":" + message);
        this.viewName = viewName;
    }

    public PageViewException(String viewName,String message, Throwable cause) {
        super(viewName + ":" + message, cause);
        this.viewName = viewName;
    }

    public PageViewException(String viewName,String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(viewName + ":" + message, cause, enableSuppression, writableStackTrace);
        this.viewName = viewName;
    }

    public String getViewName() {
        return viewName;
    }
}
