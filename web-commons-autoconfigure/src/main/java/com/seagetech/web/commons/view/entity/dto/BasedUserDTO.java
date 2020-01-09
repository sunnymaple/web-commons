package com.seagetech.web.commons.view.entity.dto;

import com.seagetech.web.bind.annotation.validated.Telephone;
import com.seagetech.web.commons.bind.Condition;
import com.seagetech.web.commons.bind.annotation.Add;
import com.seagetech.web.commons.bind.annotation.Delete;
import com.seagetech.web.commons.bind.annotation.PageView;
import com.seagetech.web.commons.bind.annotation.Query;
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
@PageView(value = DefaultViewName.USER,table = "tb_based_user", view = "vi_based_user",tableId = "user_id")
public class BasedUserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户表注解
     */
    @Delete(columnName = "user_id",deleteType = 1)
    private Integer userId;

    /**
     * 用户名，一般用于用户登录，注意和昵称的区别
     */
    @Query(condition= Condition.LIKE,label="用户名")
    @Add(label="用户名",unique = true)
    @NotNull(message = "姓名不能为空！")
    private String userName;

    /**
     * 昵称，姓名
     */
    @Query(condition= Condition.LIKE,label="姓名")
    @Add(label="姓名")
    @NotNull(message = "姓名不能为空！")
    private String nickname;

    /**
     * 状态，0：禁用或者删除 1：启用。处于0的账户不能登录
     */
    @Query(label="状态")
    @Add(label="状态",defaultValue = "1")
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
    private Integer gender;
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
    private String telephone;

    /**
     * 邮箱
     */
    @Query(condition= Condition.LIKE,label="邮箱")
    @Email
    @Add(label="邮箱")
    private String email;

    /**
     * 身份证
     */
    @Query(condition= Condition.LIKE,label="身份证")
    @Add(label="身份证")
    private String idCard;

    /**
     * 年龄，可以通过身份证号计算
     */
    private Integer age;

    /**
     * 创建时间
     */
    @Query(condition= Condition.GT_EQ,name = "startTime", label="起始时间")
    @Query(condition= Condition.LT_EQ,name = "endTime", label="截止时间")
    @Add(defaultValue = "#date|yyyy-MM-dd HH:mm:ss")
    private String createTime;
}
