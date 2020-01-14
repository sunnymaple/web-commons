package com.seagetech.web.commons.view.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author wangzb
 * @since 2020-01-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_based_menu")
public class BasedMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "menu_id", type = IdType.AUTO)
    private Integer menuId;

    /**
     * 父级菜单
     */
    private Integer parentMenuId;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 菜单别名
     */
    private String menuAlias;

    /**
     * 菜单请求uri
     */
    private String menuUri;

    /**
     * 菜单打开时的图标
     */
    private String openIcon;

    /**
     * 菜单关闭时的图标，仅限于非叶子节点菜单
     */
    private String closeIcon;

    /**
     * 菜单状态 0：禁用 1：启用
     */
    private Integer status;

    /**
     * 菜单排序，按菜单等级排序
     */
    private Integer sort;

    /**
     * 菜单描述
     */
    private String menuDetail;

    /**
     * 菜单来源，1：页面（管理系统），2：app
     */
    private Integer menySource;
    /**
     * 孩子节点
     */
    @TableField(exist = false)
    private List<BasedMenu> children;


}
