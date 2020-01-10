package com.seagetech.web.commons.login.shiro;

import org.springframework.core.Ordered;

import java.util.Set;

/**
 * 用户认证，用户获取用户权限以及密码
 * @author wangzb
 * @date 2020/1/9 10:50
 * @company 矽甲（上海）信息科技有限公司
 */
public interface IPermission extends Ordered {
    /**
     * 获取权限
     * @param userName 用户名，通常是具体登录使用的用户名（手机号或者邮箱），具有唯一性
     * @return
     */
    Set<String> getPermissions(String userName);

    /**
     * 获取用户密码
     * @param userName 用户名，通常是具体登录使用的用户名（手机号或者邮箱），具有唯一性
     * @param password 用户提交的密码（加密后）
     * @return
     */
    LoginInfo getPasswordByUserId(String userName, String password);

    /**
     * Get the order value of this object.
     * <p>Higher values are interpreted as lower priority. As a consequence,
     * the object with the lowest value has the highest priority (somewhat
     * analogous to Servlet {@code load-on-startup} values).
     * <p>Same order values will result in arbitrary sort positions for the
     * affected objects.
     *
     * @return the order value
     * @see #HIGHEST_PRECEDENCE
     * @see #LOWEST_PRECEDENCE
     */
    @Override
    default int getOrder() {
        //默认最低优先权
        return Ordered.LOWEST_PRECEDENCE;
    }
}
