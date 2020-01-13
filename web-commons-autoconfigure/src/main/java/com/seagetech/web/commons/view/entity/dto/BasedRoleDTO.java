package com.seagetech.web.commons.view.entity.dto;

import com.seagetech.web.commons.bind.annotation.PageView;
import com.seagetech.web.commons.bind.annotation.PrimaryKey;
import com.seagetech.web.commons.bind.annotation.Query;
import com.seagetech.web.commons.view.DefaultViewName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * 角色
 * @author wangzb
 * @date 2020/1/10 15:01
 * @company 矽甲（上海）信息科技有限公司
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@PageView(value = DefaultViewName.ROLE,table = "tb_based_role", view = "vi_based_role",tableId = "role_id")
public class BasedRoleDTO {
    /**
     * 角色ID
     */
    @Query
    @PrimaryKey
    private Integer roleId;
    /**
     * 角色名称
     */
    @Query(label = "角色名称")
    @NotBlank(message = "角色名称不能为空！")
    private String roleName;
    /**
     * 上级角色
     */
    @Query(label = "上级角色")
    private Integer pRoleId;
    /**
     * 上级角色名称
     */
    private String pRoleName;
}
