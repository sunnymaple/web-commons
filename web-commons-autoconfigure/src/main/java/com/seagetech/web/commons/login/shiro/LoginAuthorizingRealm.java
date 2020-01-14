package com.seagetech.web.commons.login.shiro;

import com.seagetech.common.util.SeageUtils;
import com.seagetech.web.commons.login.session.ISessionHandler;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 用户登录以及权限控制
 * @author wangzb
 * @date 2020/1/9 10:46
 * @company 矽甲（上海）信息科技有限公司
 */
@Component("authorizer")
public class LoginAuthorizingRealm extends AuthorizingRealm {

    private List<IPermission> permissions;

    @Autowired
    private ISessionHandler sessionHandler;

    public LoginAuthorizingRealm(List<IPermission> permissions) {
        this.permissions = permissions;
    }

    public LoginAuthorizingRealm() {
    }

    /**
     * 权限认证
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //登录用户信息
        Object userName = sessionHandler.getUserName();
        Set<String> results = new HashSet<>(SeageUtils.initialCapacity());
        for (IPermission permission : permissions){
            Set<String> setPermissions = permission.getPermissions(userName.toString());
            results.addAll(setPermissions);
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(results);
        return info;
    }

    /**
     * 用户登录控制
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //1. 把 AuthenticationToken 转换为 UsernamePasswordToken
        UsernamePasswordToken upToken = (UsernamePasswordToken) authenticationToken;
        //2. 从 UsernamePasswordToken 中来获取 userName
        String userName = upToken.getUsername();
        String password = String.valueOf(upToken.getPassword());
        //验证密码是否正确
        for (IPermission permission : permissions){
            LoginInfo loginInfo = permission.getPasswordByUserId(userName, password);
            if (loginInfo.isLogin()){
                return new SimpleAuthenticationInfo(loginInfo.getPrincipal(),
                        loginInfo.getPassword(),
                        ByteSource.Util.bytes(loginInfo.getCredentialsSalt()), getName());
            }
        }
        throw new AuthenticationException(userName + "," + password);
    }
}
