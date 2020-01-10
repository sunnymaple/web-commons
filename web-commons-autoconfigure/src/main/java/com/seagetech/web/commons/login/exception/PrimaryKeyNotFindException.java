package com.seagetech.web.commons.login.exception;

/**
 * 主键没有找到异常
 * @author wangzb
 * @date 2020/1/9 15:29
 * @company 矽甲（上海）信息科技有限公司
 */
public class PrimaryKeyNotFindException extends RuntimeException{
    /**
     * 视图名称
     */
    private String pageView;

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public PrimaryKeyNotFindException(String pageView) {
        super(pageView + "没有找到主键！");
        this.pageView = pageView;
    }

    public String getPageView() {
        return pageView;
    }
}
