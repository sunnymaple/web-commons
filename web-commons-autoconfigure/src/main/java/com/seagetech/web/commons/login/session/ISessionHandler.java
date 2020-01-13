package com.seagetech.web.commons.login.session;

import com.seagetech.web.commons.login.exception.NotLoginException;

/**
 * session处理器
 * @author wangzb
 * @date 2019/11/28 15:33
 * @company 矽甲（上海）信息科技有限公司
 */
public interface ISessionHandler<T> {
    /**
     * 获取session中的user对象
     * @return
     * @throws NotLoginException 建议从session中获取的对象为空时抛出该异常
     */
    T getUser() throws NotLoginException;

    /**
     * 获取用于登录的用户主键
     * @return
     * @throws NotLoginException 建议从session中获取的对象为空时抛出该异常
     */
    Object getUserName() throws NotLoginException;

    /**
     * 设置用户信息到session中
     * @param user
     */
    void setSession(T user);
}
