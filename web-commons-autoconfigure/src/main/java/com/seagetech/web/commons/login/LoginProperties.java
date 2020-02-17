package com.seagetech.web.commons.login;

import com.seagetech.web.commons.login.entity.DefaultLoginUser;
import com.seagetech.web.commons.login.shiro.ShiroUtils;
import lombok.Data;
import org.springframework.beans.BeansException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 登录相关配属性
 * @author wangzb
 * @date 2020/1/2 17:02
 * @company 矽甲（上海）信息科技有限公司
 */
@ConfigurationProperties(prefix = "login")
@Data
public class LoginProperties implements ApplicationContextAware {
    /**
     * 登录认证模式 - session
     */
    public static final String SESSION_PATTERN = "session";
    /**
     * 登录认证模式 - auth2 + jwt模式
     */
    public static final String AUTH2 = "auth2";

    private static LoginProperties loginProperties;

    public static LoginProperties getInstance(){
        return loginProperties;
    }

    /**
     * 是否启用登录自动配置
     */
    private boolean enabled = true;

    /**
     * 登录认证模式
     * 默认使用session模式
     */
    private String loginAuthPattern = SESSION_PATTERN;
    /**
     * 加密模式
     */
    private String hashAlgorithmName = ShiroUtils.HASH_ALGORITHM_NAME;
    /**
     * 加密次数
     */
    private Integer hashIterations = ShiroUtils.HASH_INTERAYION;
    /***
     * 定义不被拦截的接口
     */
    private String[] anon;
    /**
     * 登录拦截的接口
     */
    private String[] authc;
    /**
     * 登录用户实体
     * 默认值{@link DefaultLoginUser}
     */
    private Class loginUserClass = DefaultLoginUser.class;

    /**
     * 登录成功后跳转的页面
     */
    private String successPage;
    /**
     * 成功后请求路径
     */
    private String successUrl;
    /**
     * 登录页面
     */
    private String loginPage;

    /**
     * 登录页接口
     * 默认是/login/notLogin
     */
    private String loginUrl = "/login/view";

    /**
     * 登录页logo
     */
    private String loginLogo;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        loginProperties = applicationContext.getBean(LoginProperties.class);
    }

    public void setLoginUserClass(String loginUserClass) throws ClassNotFoundException {
        this.loginUserClass = Class.forName(loginUserClass);
    }
}
