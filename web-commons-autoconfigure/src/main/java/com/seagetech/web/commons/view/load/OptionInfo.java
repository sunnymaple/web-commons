package com.seagetech.web.commons.view.load;

import com.seagetech.web.commons.bind.OptionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Objects;

/**
 * 选项信息
 * @author wangzb
 * @date 2020/1/19 15:08
 * @company 矽甲（上海）信息科技有限公司
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Builder
public class OptionInfo implements IFunctionInfo{
    /**
     * 字段名称
     */
    private String name;
    /**
     * 列名称
     */
    private String columnName;
    /**
     * 选项类型
     * {@link OptionType}
     */
    private OptionType optionType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OptionInfo that = (OptionInfo) o;
        return optionType == that.optionType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(optionType);
    }
}
