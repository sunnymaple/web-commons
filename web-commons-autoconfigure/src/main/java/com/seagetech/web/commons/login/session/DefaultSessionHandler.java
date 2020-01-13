package com.seagetech.web.commons.login.session;

import com.seagetech.web.commons.login.entity.DefaultLoginUser;
import com.seagetech.web.commons.login.exception.NotLoginException;

/**
 * 默认的session处理器
 * @author wangzb
 * @date 2020/1/10 12:00
 * @company 矽甲（上海）信息科技有限公司
 */
public class DefaultSessionHandler extends AbstractSessionHandler<DefaultLoginUser>{
    /**
     * 获取session中的user对象
     *
     * @return
     */
    @Override
    public DefaultLoginUser getUser() throws NotLoginException{
        DefaultLoginUser user = (DefaultLoginUser) session.getAttribute(USER_SESSION_NAME);
        if (user == null){
            throw new NotLoginException();
        }
        return user;
    }

    /**
     * 获取用户名
     *
     * @return
     */
    @Override
    public Object getUserName() throws NotLoginException{
        return getUser().getUserId() + "";
    }

    /**
     * 设置用户信息到session中
     *
     * @param user
     */
    @Override
    public void setSession(DefaultLoginUser user) {
        session.setAttribute(USER_SESSION_NAME,user);
    }
}
