package com.seagetech.web.commons.login.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * 登录实体
 * @author wangzb
 * @date 2020/1/9 18:26
 * @company 矽甲（上海）信息科技有限公司
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginVO {
    /**
     * 登录用户名
     */
    @NotBlank(message = "用户名不能为空！")
    private String userName;
    /**
     * 登录凭证密码
     */
    @NotBlank(message = "密码不能为空！")
    private String password;
    /**
     * 记住我
     */
    private Integer rememberMe;
}
