package com.seagetech.web.commons.login;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 登录相关配属性
 * @author wangzb
 * @date 2020/1/2 17:02
 * @company 矽甲（上海）信息科技有限公司
 */
@ConfigurationProperties(prefix = "login")
@Data
public class LoginProperties {
    /**
     * 登录认证模式 - session
     */
    public static final String SESSION_PATTERN = "session";
    /**
     * 登录认证模式 - auth2 + jwt模式
     */
    public static final String AUTH2 = "auth2";

    /**
     * 登录认证模式
     * 默认使用session模式
     */
    private String loginAuthPattern = SESSION_PATTERN;
}
