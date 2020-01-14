package com.seagetech.web.commons.view.entity.dto;

import com.seagetech.web.commons.bind.Condition;
import com.seagetech.web.commons.bind.annotation.Add;
import com.seagetech.web.commons.bind.annotation.PageView;
import com.seagetech.web.commons.bind.annotation.PrimaryKey;
import com.seagetech.web.commons.bind.annotation.Query;
import com.seagetech.web.commons.view.DefaultViewName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 机构
 * @author wangzb
 * @date 2020/1/13 16:45
 * @company 矽甲（上海）信息科技有限公司
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@PageView(value = DefaultViewName.GROUP,table = "tb_based_group", view = "vi_based_group",tableId = "group_id")
public class BasedGroupDTO implements Serializable {
    /**
     * 角色ID
     */
    @Query
    @PrimaryKey
    private Integer groupId;
    /**
     * 角色名称
     */
    @Query(label = "机构名称",condition = Condition.LIKE)
    @NotBlank(message = "机构名称不能为空！")
    @Add(label = "机构名称")
    private String groupName;
    /**
     * 上级角色
     */
    @Query(label = "上级机构")
    @Add(label = "上级机构",defaultValue = "0")
    private Integer pGroupId;
    /**
     * 上级角色名称
     */
    private String pGroupName;
}
