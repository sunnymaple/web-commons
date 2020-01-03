package com.seagetech.web.commons.login.session;

/**
 * @author wangzb
 * @date 2019/11/28 15:33
 * @company 矽甲（上海）信息科技有限公司
 */
public interface ISessionHandler<T> {
    /**
     * 获取session中的user对象
     * @return
     */
    T getUser();

    /**
     * 获取用户名
     * @return
     */
    String getUserName();

    /**
     * 设置用户信息到session中
     * @param user
     */
    void setSession(T user);
}
