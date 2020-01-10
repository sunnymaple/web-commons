package com.seagetech.web.commons.login.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 登录后用户实体，默认
 * @author wangzb
 * @date 2020/1/10 11:48
 * @company 矽甲（上海）信息科技有限公司
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DefaultLoginUser implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 用户表注解
     */
    private Integer userId;

    /**
     * 用户名，一般用于用户登录，注意和昵称的区别
     */
    private String userName;

    /**
     * 昵称，姓名
     */
    private String nickname;

    /**
     * 状态，0：禁用或者删除 1：启用。处于0的账户不能登录
     */
    private Integer status;
    /**
     * 状态名称
     */
    private String statusText;

    /**
     * 性别，0：女 1：男
     */
    private Integer gender;
    /**
     * 性别名称
     */
    private String genderText;

    /**
     * 手机号或者联系方式，有些项目可能会使用手机号登录
     */
    private String telephone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 身份证
     */
    private String idCard;

    /**
     * 年龄，可以通过身份证号计算
     */
    private Integer age;

    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 创建人
     */
    private Integer createUserId;
}
