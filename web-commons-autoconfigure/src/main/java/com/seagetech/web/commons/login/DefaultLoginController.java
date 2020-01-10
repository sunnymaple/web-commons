package com.seagetech.web.commons.login;

import com.seagetech.web.commons.login.entity.vo.LoginVO;
import com.seagetech.web.commons.login.exception.NotLoginException;
import com.seagetech.web.commons.login.session.ISessionHandler;
import com.seagetech.web.commons.login.shiro.ShiroUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Objects;

/**
 * 默认的登录接口
 * @author wangzb
 * @date 2020/1/9 16:35
 * @company 矽甲（上海）信息科技有限公司
 */
@RestController
@RequestMapping("/login")
@Validated
public class DefaultLoginController {

    @Autowired
    private ISessionHandler sessionHandler;

    /**
     * 登录
     * @param loginVO 登录实体对象
     * @return
     */
    @PostMapping(produces = "application/json")
    public Object login(@Valid @RequestBody LoginVO loginVO){
        ShiroUtils.login(loginVO.getUserName(),loginVO.getPassword());
        Subject currentUser = SecurityUtils.getSubject();
        Object principal = currentUser.getPrincipal();
        //添加session
        if (Objects.equals(LoginProperties.getInstance().getLoginAuthPattern(),LoginProperties.SESSION_PATTERN)){
            sessionHandler.setSession(principal);
        }
        return principal;
    }

    /**
     * 未登录或者登录超时
     */
    @RequestMapping("/notLogin")
    public void notLogin(){
        throw new NotLoginException();
    }

    /**
     * 退出登录
     * @return
     */
    @RequestMapping(value = "/logout",produces = "application/json")
    public String logout() {
        return "退出成功！";
    }
}
