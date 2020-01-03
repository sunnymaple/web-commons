package com.seagetech.web.commons.view.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 *  用户
 * </p>
 *
 * @author wangzb
 * @since 2019-12-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_based_user")

public class BasedUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户表注解
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;

    /**
     * 用户名，一般用于用户登录，注意和昵称的区别
     */
    private String userName;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 昵称，姓名
     */
    private String nickname;

    /**
     * 状态，0：禁用或者删除 1：启用。处于0的账户不能登录
     */
    private Integer status;

    /**
     * 性别，0：女 1：男
     */
    private Integer gender;

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
}
