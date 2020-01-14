package com.seagetech.web.commons.view.entity.dto;

import com.seagetech.web.bind.annotation.validated.Telephone;
import com.seagetech.web.commons.bind.Condition;
import com.seagetech.web.commons.bind.annotation.*;
import com.seagetech.web.commons.view.DefaultViewName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
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
@PageView(value = DefaultViewName.USER,table = "tb_based_user", view = "vi_based_user",tableId = "user_id",viewPath = "system/user")
public class BasedUserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户表注解
     */
    @Delete(columnName = "user_id",deleteType = 1)
    @Query
    @PrimaryKey
    private Integer userId;

    /**
     * 用户名，一般用于用户登录，注意和昵称的区别
     */
    @Query(condition= Condition.LIKE,label="用户名")
    @Add(label="用户名",unique = true)
    @NotNull(message = "姓名不能为空！")
    @Import(col = 1)
    @UseLoginUserName
    @Export(col = 1,headName = "用户名")
    private String userName;

    /**
     * 昵称，姓名
     */
    @Query(condition= Condition.LIKE,label="姓名")
    @Add(label="姓名")
    @NotNull(message = "姓名不能为空！")
    @Import(col = 2)
    @Export(col = 2,headName = "姓名")
    private String nickname;

    /**
     * 状态，0：禁用或者删除 1：启用。处于0的账户不能登录
     */
    @Query(label="状态")
    @Add(label="状态",defaultValue = "1")
    @UserStatus(value = "1",valid = true)
    @UserStatus(value = "2",message = "用户已过期！")
    @UserStatus(value = "0",message = "用户名或密码错误！")
    private Integer status;
    /**
     * 状态名称
     */
    private String statusText;

    /**
     * 性别，0：女 1：男
     */
    @Query(label="性别")
    @Add(label="性别")
    @NotNull(message = "性别不能为空！")
    @Import(col = 3)
    @Export(col = 3,headName = "性别")
    private String gender;
    /**
     * 性别名称
     */
    private String genderText;

    /**
     * 手机号或者联系方式，有些项目可能会使用手机号登录
     */
    @Query(condition= Condition.LIKE,label="手机号码")
    @Add(label="手机号码",unique = true)
    @Telephone
    @NotNull(message = "手机号不能为空！")
    @Import(col = 4)
    @UseLoginUserName
    @Export(col = 4,headName = "手机号")
    private String telephone;

    /**
     * 邮箱
     */
    @Query(condition= Condition.LIKE,label="邮箱")
    @Email
    @Add(label="邮箱")
    @Import(col = 5)
    @Export(col = 5,headName = "邮箱")
    private String email;

    /**
     * 身份证
     */
    @Query(condition= Condition.LIKE,label="身份证")
    @Add(label="身份证")
    @Import(col = 6)
    @Export(col = 6,headName = "身份证")
    private String idCard;

    /**
     * 年龄，可以通过身份证号计算
     */
    @Import(col = 7)
    @Export(col = 8,headName = "年龄")
    private Integer age;

    /**
     * 创建时间
     */
    @Query(condition= Condition.GT_EQ,name = "startTime", label="起始时间")
    @Query(condition= Condition.LT_EQ,name = "endTime", label="截止时间")
    @Add(defaultValue = "#date|yyyy-MM-dd HH:mm:ss")
    @Import(col = -1,defaultValue = "#date|yyyy-MM-dd HH:mm:ss")
    private String createTime;
    /**
     * 密码
     */
    @Add(defaultValue = "#password")
    @Import(col = -1,defaultValue = "#password")
    @Password
    private String password;
    /**
     * 创建人
     */
    @Add(defaultValue = "#user")
    @Import(col = -1,defaultValue = "#user")
    private Integer createUserId;
}
