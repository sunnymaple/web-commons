package com.seagetech.web.commons.login;

import com.seagetech.common.util.SeageUtils;
import com.seagetech.web.commons.login.entity.DefaultLoginUser;
import com.seagetech.web.commons.login.exception.LoginExceptionHandler;
import com.seagetech.web.commons.login.exception.NotSupportLoginAuthPatternException;
import com.seagetech.web.commons.login.session.DefaultSessionHandler;
import com.seagetech.web.commons.login.session.ISessionHandler;
import com.seagetech.web.commons.login.shiro.IPermission;
import com.seagetech.web.commons.login.shiro.LoginAuthorizingRealm;
import com.seagetech.web.commons.login.shiro.ShiroUtils;
import com.seagetech.web.commons.login.shiro.UseUserNameLoginPermission;
import com.seagetech.web.commons.view.service.PageViewService;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.servlet.Filter;
import java.util.*;

/**
 * 登录自动配置类
 * @author wangzb
 * @date 2020/1/2 17:00
 * @company 矽甲（上海）信息科技有限公司
 */
@Configuration
@ConditionalOnWebApplication
@EnableConfigurationProperties(LoginProperties.class)
@ConditionalOnProperty(prefix="login",name = "enabled", havingValue = "true",matchIfMissing = true)
@Import({DefaultLoginController.class, LoginExceptionHandler.class})
public class LoginAutoConfiguration implements ApplicationContextAware {

    @Autowired
    private LoginProperties loginProperties;

    private ApplicationContext applicationContext;

    @Autowired
    private List<IPermission> permissions;

    /**
     * 默认的不会被拦截的链接
     * <p>/login/** 登录页面的相关操作路径</p>
     * <p>/favicon.ico ico小图标，用于页面title</p>
     * <p>/webjars/**  webjars为前端库（比如Jquery & Bootstrap）的静态文件,然后打包成jar文件</p>
     */
    private static final String[] ANON_DEFAULT = {"/login/**","/favicon.ico","/webjars/**","/static/**"};

    /**
     * 默认拦截的链接
     */
    private static final String[] AUTHC_DEFAULT = {"/**"};

    /**
     * 不被拦截的anon
     */
    private static final String ANON = "anon";
    /**
     * 登录拦截authc
     */
    private static final String AUTHC = "authc";

    /**
     * 初始化操作
     */
    @PostConstruct
    public void init(){
        String loginAuthPattern = loginProperties.getLoginAuthPattern();
        if (Objects.equals(loginAuthPattern,LoginProperties.SESSION_PATTERN)){
            applicationContext.getBean(ISessionHandler.class);
        }else {
            throw new NotSupportLoginAuthPatternException(loginAuthPattern);
        }
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Bean
    @ConditionalOnMissingBean(ISessionHandler.class)
    @ConditionalOnProperty(prefix="login",name = "loginAuthPattern", havingValue = LoginProperties.SESSION_PATTERN,matchIfMissing = true)
    public ISessionHandler sessionHandler(){
        return new DefaultSessionHandler();
    }

    @Bean(name = "shiroAuthorizingRealm")
    public LoginAuthorizingRealm realm(HashedCredentialsMatcher matcher){
        LoginAuthorizingRealm loginAuthorizingRealm = new LoginAuthorizingRealm(permissions);
        loginAuthorizingRealm.setCredentialsMatcher(matcher);
        return loginAuthorizingRealm;
    }

    /**
     * 添加默认不拦截的路径
     * 包括可能存在的静态资源
     * @param filterChainDefinitionMap
     */
    private void anon(Map<String,String> filterChainDefinitionMap){
        //系统定义的相关接口
        Arrays.stream(ANON_DEFAULT).forEach(anon->filterChainDefinitionMap.put(anon,ANON));
        //用户定义
        Optional<String[]> anonsOp = Optional.ofNullable(loginProperties.getAnon());
        anonsOp.ifPresent(anons->Arrays.stream(anons).forEach(anon->filterChainDefinitionMap.put(anon,ANON)));
    }

    /**
     * 配置登录拦截的接口
     * @param filterChainDefinitionMap
     */
    private void authc(Map<String,String> filterChainDefinitionMap){
        String[] authcs = loginProperties.getAuthc();
        if (SeageUtils.isEmpty(authcs)){
            authcs = AUTHC_DEFAULT;
        }
        Arrays.stream(authcs).forEach(authc->filterChainDefinitionMap.put(authc,AUTHC));
    }

    /**
     * 定义拦截器,设置shiro的过滤规则
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 拦截器。匹配原则是最上面的最优先匹配
        Map<String,String> filterChainDefinitionMap = new LinkedHashMap<>();

        //1、配置不会被拦截的链接
        anon(filterChainDefinitionMap);
        //2、配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
        filterChainDefinitionMap.put("/logout", "logout");
        //3、需要身份认证的接口
        authc(filterChainDefinitionMap);
        //4、如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl(loginProperties.getLoginUrl());
        //5、没有权限跳转的页面
        shiroFilterFactoryBean.setUnauthorizedUrl("");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        //6、自定义退出过滤器
        Map<String, Filter> filters = new HashMap<>(1);
        LogoutFilter logoutFilter = new LogoutFilter();
        filters.put("logout",logoutFilter);
        //退出后跳转的页面
        logoutFilter.setRedirectUrl("/login/logout");
        shiroFilterFactoryBean.setFilters(filters);
        return shiroFilterFactoryBean;
    }

    /**
     * 将Realm转交给SecurityManager
     * @param matcher
     * @return
     */
    @Bean
    public SecurityManager securityManager(@Qualifier("hashedCredentialsMatcher") HashedCredentialsMatcher matcher){
        DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
        securityManager.setRealm(realm(matcher));
        return securityManager;
    }

    /**
     * 密码匹配凭证管理器
     *
     * @return
     */
    @Bean(name = "hashedCredentialsMatcher")
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        // 采用的加密方式
        String hashAlgorithmName = StringUtils.isEmpty(loginProperties.getHashAlgorithmName())
                ? ShiroUtils.HASH_ALGORITHM_NAME : loginProperties.getHashAlgorithmName();
        hashedCredentialsMatcher.setHashAlgorithmName(hashAlgorithmName);
        // 设置加密次数
        int hashIterations = StringUtils.isEmpty(loginProperties.getHashIterations())
                ?  ShiroUtils.HASH_INTERAYION : loginProperties.getHashIterations();
        hashedCredentialsMatcher.setHashIterations(hashIterations);
        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);
        return hashedCredentialsMatcher;
    }

    /**
     * 权限
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
        daap.setProxyTargetClass(true);
        return daap;
    }

    /**
     * 权限
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
        aasa.setSecurityManager(securityManager);
        return aasa;
    }

    @Bean
    public UseUserNameLoginPermission permission(PageViewService pageViewService){
        return new UseUserNameLoginPermission(pageViewService);
    }
}
