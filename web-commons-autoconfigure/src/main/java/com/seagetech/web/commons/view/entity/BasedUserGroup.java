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
@TableName("tb_based_user_group")
public class BasedUserGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户组关联关系表
     */
    @TableId(value = "user_group_id", type = IdType.AUTO)
    private Integer userGroupId;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 组ID
     */
    private Integer groupId;


}
