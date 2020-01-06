package com.seagetech.web.commons.bind.annotation;

import com.alibaba.fastjson.JSONObject;
import com.seagetech.web.commons.view.entity.LogOperateVo;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @author gdl
 * @date 2020/1/6 11:15
 * @company 矽甲（上海）信息科技有限公司
 */

@Aspect
@Component
public class OperateLogAspect {

    @Resource
    private ApplicationContext applicationContext;

    @Pointcut(value = "@annotation(com.seagetech.web.commons.bind.annotation.OperateLog)")
    public void dataLog() {

    }

    @Around("dataLog()")
    public Object doAfter(ProceedingJoinPoint joinPoint) throws Throwable {

        Object proceed = joinPoint.proceed();
        //获取用户名
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        JSONObject userName = (JSONObject) request.getSession().getAttribute("user");
        //注解及操作信息
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        OperateLog annotation = method.getAnnotation(OperateLog.class);
        Class<?> aClass = annotation.objClass();
        //封装操作对象
        LogOperateVo logOperateVo = new LogOperateVo();
        logOperateVo.setOperateType(annotation.operateType());
        logOperateVo.setOperateDetail(annotation.detail());
        logOperateVo.setCreateUserId(Integer.valueOf(userName.get("userId").toString()));
        OperateLogService bean = (OperateLogService) applicationContext.getBean(aClass);
        bean.logOperate(logOperateVo);
        return proceed;
    }
}