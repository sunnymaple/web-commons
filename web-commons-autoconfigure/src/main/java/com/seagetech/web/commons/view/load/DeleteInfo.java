package com.seagetech.web.commons.view.load;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/** 删除类
 * @author gdl
 * @date 2020/1/6 16:49
 * @company 矽甲（上海）信息科技有限公司
 */
@Data
@Builder
@Accessors(chain = true)
public class DeleteInfo implements IFunctionInfo{
    /**
     * 参数名称，默认为属性名
     */
    private String name;
    /**
     * 数据库列名称，默认为属性"_"形式 主键
     */
    private String columnName;
    /**
     * 主键ID 避免误删，默认给 -1
     */
    private Integer deleteId = -1;
    /**
     * 删除类型 0 物理 1 逻辑
     */
    private Integer deleteType = 0;

    /**
     * 数据库列名称，默认为属性"_"形式 状态
     */
    private String statusName;
}
