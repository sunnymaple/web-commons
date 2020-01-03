package com.seagetech.web.commons.view.load;

import com.seagetech.web.commons.bind.Condition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 查询
 * @author wangzb
 * @date 2019/12/27 9:39
 * @company 矽甲（上海）信息科技有限公司
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryInfo implements IFunctionInfo {
    /**
     * 查询条件连接符
     * {@link Condition}
     */
    private Condition condition;
    /**
     * 参数名称
     */
    private String name;
    /**
     * 列表名称
     */
    private String columnName;
}
