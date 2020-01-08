package com.seagetech.web.commons.view.controller;

import com.seagetech.common.exception.DefaultException;
import com.seagetech.common.util.HttpStatusTypeEnum;
import com.seagetech.common.util.SeageJson;
import com.seagetech.web.commons.util.Utils;
import com.seagetech.web.commons.view.load.PageViewContainer;
import com.seagetech.web.commons.view.load.PageViewInfo;
import com.seagetech.web.exception.ParamVerifyException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Map;
import java.util.Set;

/**
 * @author wangzb
 * @date 2020/1/8 12:34
 * @company 矽甲（上海）信息科技有限公司
 */
@Aspect
@Component
public class ParamValidatedAspect {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private Validator validator;

    /**
     * 定义切点
     */
    @Pointcut(value = "@annotation(com.seagetech.web.commons.view.controller.ParamValidated)")
    public void pointcut() {}

    /**
     * 切面
     * @param joinPoint
     */
    @Before(value = "pointcut()")
    public <T> void before(JoinPoint joinPoint){
        // 获取传入参数值
        Object[] args = joinPoint.getArgs();
        String viewName = (String) args[0];
        //
        PageViewContainer pageViewContainer = PageViewContainer.getInstance();
        PageViewInfo pageViewInfo = pageViewContainer.get(viewName);
        //验证实体
        T validateEntity = null;
        try {
            //验证实体Class
            Class<T> validateEntityClass = pageViewInfo.getPageViewClass();
            //获取请求参数
            Map<String, Object> params = Utils.getParameter(request);
            //反射创建实体对象
            validateEntity = SeageJson.map2Object(params, validateEntityClass);
        }catch (Exception e){
            throw new DefaultException(e, HttpStatusTypeEnum.INTERNAL_SERVER_ERROR.value());
        }
        //参数验证
        validateBean(validateEntity);
    }

    /**
     * 参数验证
     * @param t 验证参数的类
     * @param groups
     * @param <T>
     */
    public <T> void validateBean(T t,Class<?>...groups) {
        Set<ConstraintViolation<T>> violationSet = validator.validate(t,groups);
        boolean hasError = violationSet != null && violationSet.size() > 0;
        if (hasError) {
            for (ConstraintViolation<T> violation : violationSet) {
                throw new ParamVerifyException(violation.getMessage());
            }
        }
    }

}
