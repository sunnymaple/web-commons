package com.seagetech.web.commons.login;

import com.seagetech.web.commons.login.exception.NotSupportLoginAuthPatternException;
import com.seagetech.web.commons.login.session.ISessionHandler;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Objects;

/**
 * 登录自动配置类
 * @author wangzb
 * @date 2020/1/2 17:00
 * @company 矽甲（上海）信息科技有限公司
 */
@Configuration
@ConditionalOnWebApplication
@EnableConfigurationProperties(LoginProperties.class)
public class LoginAutoConfiguration implements ApplicationContextAware {

    @Autowired
    private LoginProperties loginProperties;

    private ApplicationContext applicationContext;

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
}
