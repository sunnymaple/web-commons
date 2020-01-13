package com.seagetech.web.commons.login.exception;

import com.seagetech.web.exception.AbstractExceptionHandler;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.HandlerMethod;

/**
 * 登录异常处理
 * @author wangzb
 * @date 2020/1/10 15:15
 * @company 矽甲（上海）信息科技有限公司
 */
@ControllerAdvice
public class LoginExceptionHandler extends AbstractExceptionHandler {
    /**
     * shiro登录验证异常
     * @param e
     * @param handlerMethod
     * @return
     */
    @ExceptionHandler(AuthenticationException.class)
    public String constraintViolationException(AuthenticationException e, HandlerMethod handlerMethod){
        LoginException loginException = new LoginException(e.getMessage());
        setAttribute(loginException,loginException.getStatus());
        return forward(handlerMethod);
    }
}
