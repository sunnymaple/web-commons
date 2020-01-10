package com.seagetech.web.commons.view.load;

import com.seagetech.web.commons.view.load.IFunctionInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wangzb
 * @date 2020/1/9 13:34
 * @company 矽甲（上海）信息科技有限公司
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DefaultInfo implements IFunctionInfo {
    /**
     * 字段名称
     */
    private String name;
    /**
     * 列名称
     */
    private String columnName;
}
