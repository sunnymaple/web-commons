package com.seagetech.web.commons.view.load;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/** 导入注解信息
 * @author gdl
 * @date 2020/1/8 15:04
 * @company 矽甲（上海）信息科技有限公司
 */
@Data
@Builder
@Accessors(chain = true)
public class ImportInfo implements IFunctionInfo {

    /**
     * 字段类型
     */
    private Class<? extends Serializable> fieldType;
    /**
     * 参数名称，默认为属性名
     */
    private String name;
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
    /**
     * 导入内容是否有和字典表关联的内容
     */
    private  Class<?> option;
}
