package com.seagetech.web.commons.login.session;

import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;

/**
 * session
 * @author wangzb
 * @date 2019/11/28 15:35
 * @company 矽甲（上海）信息科技有限公司
 */
public abstract class DefaultSessionHandler<T> implements ISessionHandler<T>{
    /**
     * 注入HttpSession
     */
    @Autowired
    protected HttpSession session;

    /**
     * 用户的session的名称
     */
    protected static final String USER_SESSION_NAME = "userSession";
}
