package com.seagetech.web.commons.login;

import com.seagetech.common.util.HttpStatusTypeEnum;
import com.seagetech.common.util.SeageUtils;
import com.seagetech.web.commons.login.entity.vo.LoginVO;
import com.seagetech.web.commons.login.exception.NotLoginException;
import com.seagetech.web.commons.login.session.ISessionHandler;
import com.seagetech.web.commons.login.shiro.ShiroUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Objects;

/**
 * 默认的登录接口
 * @author wangzb
 * @date 2020/1/9 16:35
 * @company 矽甲（上海）信息科技有限公司
 */
@Controller
@RequestMapping("/login")
@Validated
public class DefaultLoginController {

    @Autowired(required = false)
    private ISessionHandler sessionHandler;

    @Autowired
    private LoginProperties loginProperties;

    @Autowired
    private HttpServletRequest request;

    /**
     * 登录
     * @param loginVO 登录实体对象
     * @return
     */
    @PostMapping(produces = "application/json")
    @ResponseBody
    public Object login(@Valid @RequestBody LoginVO loginVO){
        //是否记住我
        Integer rememberMeInt = loginVO.getRememberMe();
        boolean rememberMe = (rememberMeInt!=null && rememberMeInt == 1) ? true : false;
        ShiroUtils.login(loginVO.getUserName(),loginVO.getPassword(),rememberMe);
        Subject currentUser = SecurityUtils.getSubject();
        Object principal = currentUser.getPrincipal();
        //添加session
        if (Objects.equals(LoginProperties.getInstance().getLoginAuthPattern(),LoginProperties.SESSION_PATTERN)){
            sessionHandler.setSession(principal);
        }
        return principal;
    }

    /**
     * 登录
     * @param loginVO
     * @return
     */
    @RequestMapping(produces = "text/html")
    public String loginView(@Valid LoginVO loginVO, ModelMap modelMap){
        try {
            login(loginVO);
        }catch (AuthenticationException e){
            modelMap.put("message", "用户名或密码错误！");
            modelMap.put("loginLogo", loginProperties.getLoginLogo());
            return loginProperties.getLoginPage();
        }catch (Exception e){
            modelMap.put("loginLogo", loginProperties.getLoginLogo());
            modelMap.put("message", HttpStatusTypeEnum.INTERNAL_SERVER_ERROR.getReasonPhrase());
            return loginProperties.getLoginPage();
        }
        String successUrl = StringUtils.defaultIfBlank(loginVO.getLoginSuccessPath(),loginProperties.getSuccessUrl());
        if (!SeageUtils.isEmpty(successUrl)){
            return "redirect:" + successUrl;
        }
        return loginProperties.getSuccessPage();
    }

    /**
     * 登录页接口
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/notLogin",produces = "text/html")
    public String login(ModelMap modelMap,String url){
        modelMap.put("loginLogo", loginProperties.getLoginLogo());
        modelMap.put("url",url);
        return loginProperties.getLoginPage();
    }

    /**
     * 登录页接口
     * @return
     */
    @RequestMapping(value = "/view",produces = "application/json")
    @ResponseBody
    public String login(){
        throw new NotLoginException();
    }

    /**
     * 未登录或者登录超时
     */
    @RequestMapping("/notLogin")
    @ResponseBody
    public void notLogin(){
        throw new NotLoginException();
    }

    /**
     * 登录页接口
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/notLogin",produces = "text/html")
    public String notLogin(ModelMap modelMap){
        modelMap.put("loginLogo", loginProperties.getLoginLogo());
        return loginProperties.getLoginPage();
    }

    /**
     * 退出登录
     * @return
     */
    @RequestMapping(value = "/logout",produces = "application/json")
    @ResponseBody
    public String logout() {
        return "退出成功！";
    }

    /**
     * 退出登录
     * @return
     */
    @RequestMapping(value = "/logout",produces = "text/html")
    public String logoutHtml() {
        return "redirect:/login/view";
    }
}
