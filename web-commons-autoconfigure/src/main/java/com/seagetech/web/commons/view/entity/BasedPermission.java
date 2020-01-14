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
 *
 * </p>
 *
 * @author wangzb
 * @since 2020-01-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_based_permission")
public class BasedPermission implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 许可主键ID
     */
    @TableId(value = "permission_id", type = IdType.AUTO)
    private Integer permissionId;

    /**
     * 许可名称，建议中文说明
     */
    private String permissionName;

    /**
     * 许可别名，建议英文大写加下划线形式
     */
    private String permissionAlias;

    /**
     * 许可类型,1：菜单权限，用于管理系统  2：功能权限，可用于管理系统和app接口端
     */
    private Integer permissionType;

    /**
     * 菜单主键ID，非管理系统菜单或者权限许可证可以不指定
     */
    private Integer menuId;


}
