package com.seagetech.web.commons.login.shiro;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 登录用户信息
 * @author wangzb
 * @date 2020/1/9 16:07
 * @company 矽甲（上海）信息科技有限公司
 */
@Data
@Builder
@Accessors(chain = true)
public class LoginInfo {
    /**
     * 用户实体
     * 通常为用户实体对象或者用于登录的用户名
     */
    private Object principal;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 盐值
     * 通常为主键
     */
    private Object credentialsSalt;
    /**
     * 是否登录成功
     */
    private boolean login = true;

}
