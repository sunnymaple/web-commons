package com.seagetech.web.commons.view.load;

import com.seagetech.web.commons.bind.FunctionType;
import com.seagetech.web.commons.bind.annotation.Resolver;
import com.seagetech.web.commons.view.load.resolver.ImportResolver;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.lang.annotation.*;

/** 导出注解信息
 * @author gdl
 * @date 2020/1/13 9:08
 * @company 矽甲（上海）信息科技有限公司
 */
@Data
@Builder
@Accessors(chain = true)
public class ExportInfo implements IFunctionInfo {
    /**
     * excel表格头名称
     */
    private String  headName;
    private String  name;
    /**
     * 数据库列名称，默认为属性"_"形式
     */
    private String columnName;
    /**
     * 第几列
     */
    private Integer col;
    /**
     * 默认值
     * 可以指定具体的值，如1,2这样的数字，或者指定一个字符串："张三"、"abc"
     */
    private String defaultValue;
}
