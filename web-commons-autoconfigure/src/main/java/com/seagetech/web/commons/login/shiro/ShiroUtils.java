package com.seagetech.web.commons.login.shiro;

import com.seagetech.common.util.SeageUtils;
import com.seagetech.web.commons.login.LoginProperties;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;

/**
 * Shiro工具类
 * @author wangzb
 * @date 2018-11-01 12:36
 * @company 矽甲（上海）信息科技有限公司
 */
public class ShiroUtils {

    /**
     * 默认的加密次数
     */
    public static final int HASH_INTERAYION = 1024;


    /**
     * 默认的加密方式
     */
    public static final String HASH_ALGORITHM_NAME = "MD5";

    /**
     * 获取加密后的密码
     * @param algorithmName 加密方式
     * @param iterations 累加加密次数
     * @param password 密码
     * @param userName 用户名，用于添加验证，即使密码相同，不同用户名加密后的密码是不同的
     * @return
     */
    public static String encryption(String algorithmName, Integer iterations,
                             String password,String userName) {
        if (SeageUtils.isEmpty(algorithmName)){
            algorithmName = HASH_ALGORITHM_NAME;
        }
        if (SeageUtils.isEmpty(iterations)){
            iterations = HASH_INTERAYION;
        }
        if (SeageUtils.isEmpty(password)){
            return "";
        }
        Object salt = ByteSource.Util.bytes(userName);
        Object result = new SimpleHash(algorithmName, password, salt, iterations);
        return result.toString();
    }

    /**
     * 加密
     * @param password 密码
     * @param userName 颜值，通常为用户主键
     * @return
     */
    public static String encryption(String password,String userName){
        LoginProperties loginProperties = LoginProperties.getInstance();
        return encryption(loginProperties.getHashAlgorithmName(),loginProperties.getHashIterations(),password,userName);
    }


    /**
     * 用户登录
     * @param userName 用户名
     * @param password 密码
     * @param rememberMe 记住我
     * @throws Exception
     */
    public static void login(String userName,String password,boolean rememberMe){
        //获取当前登录对象
        Subject currentUser = SecurityUtils.getSubject();
        //判断是否认证（登录）
        if (!currentUser.isAuthenticated()) {
            // 把用户名和密码封装为 UsernamePasswordToken 对象
            UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
            token.setRememberMe(rememberMe);
            //执行登录
            currentUser.login(token);
        }
    }

}
