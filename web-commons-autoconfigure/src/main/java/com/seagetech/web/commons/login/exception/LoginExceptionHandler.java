package com.seagetech.web.commons.login.exception;

import com.seagetech.web.commons.login.session.ISessionHandler;
import com.seagetech.web.exception.AbstractExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录异常处理
 * @author wangzb
 * @date 2020/1/10 15:15
 * @company 矽甲（上海）信息科技有限公司
 */
@ControllerAdvice
@Slf4j
public class LoginExceptionHandler extends AbstractExceptionHandler {

    @Autowired
    private ISessionHandler sessionHandler;

    @Autowired
    private HttpServletRequest request;

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

    /**
     * 没有权限
     * @param e
     * @param handlerMethod
     * @return
     */
    @ExceptionHandler(UnauthorizedException.class)
    public String unauthorizedException(UnauthorizedException e, HandlerMethod handlerMethod){
        UnAuthorizedException unAuthorizedException = new UnAuthorizedException(sessionHandler.getUserName(),request.getServletPath(),e);
        setAttribute(unAuthorizedException,unAuthorizedException.getStatus());
        log.info("userId" + sessionHandler.getUserName() + ",path:" + request.getServletPath());
        return forward(handlerMethod);
    }
}
