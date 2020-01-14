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
@TableName("tb_based_group")
public class BasedGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 组织机构，公司部门等主键ID
     */
    @TableId(value = "group_id", type = IdType.AUTO)
    private Integer groupId;

    /**
     * 名称
     */
    private String groupName;

    /**
     * 上级机构，用于机构分组
     */
    private Integer pGroupId;


}
