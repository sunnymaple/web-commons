package com.seagetech.web.commons.view.entity.dto;

import com.seagetech.web.commons.bind.annotation.Add;
import com.seagetech.web.commons.bind.annotation.PageView;
import com.seagetech.web.commons.bind.annotation.PrimaryKey;
import com.seagetech.web.commons.bind.annotation.Query;
import com.seagetech.web.commons.view.DefaultViewName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 用户角色关联关系表
 * @author wangzb
 * @date 2020/1/13 16:56
 * @company 矽甲（上海）信息科技有限公司
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@PageView(value = DefaultViewName.USER_ROLE,table = "tb_based_user_role", view = "vi_based_user_role",tableId = "user_role_id")
public class BasedUserRoleDTO implements Serializable {
    /**
     * 用户角色关联表主键ID
     */
    @PrimaryKey
    private Integer userRoleId;

    /**
     * 用户ID
     */
    @Query
    @Add
    private Integer userId;

    /**
     * 角色ID
     */
    @Query
    @Add
    private Integer roleId;

    /**
     * 角色名称
     */
    private String roleName;
}
