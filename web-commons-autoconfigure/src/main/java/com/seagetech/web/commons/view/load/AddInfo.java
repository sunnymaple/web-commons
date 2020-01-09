package com.seagetech.web.commons.view.load;

import com.seagetech.web.commons.bind.annotation.Add;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.List;

/**
 * 添加功能信息
 * @author wangzb
 * @date 2020/1/6 15:12
 * @company 矽甲（上海）信息科技有限公司
 */
@Data
@Builder
@Accessors(chain = true)
public class AddInfo implements IFunctionInfo{
    /**
     * 字段类型
     */
    private Class<? extends Serializable> fieldType;
    /**
     * 参数名称，默认为属性名
     * @return
     */
    private String name;
    /**
     * 数据库列名称，默认为属性"_"形式
     * @return
     */
    private String columnName;
    /**
     * 唯一约束
     */
    private boolean unique;
    /**
     * 默认值
     * {@link Add#defaultValue()}
     */
    private String defaultValue;
    /**
     * 页面查询条件的label的名称
     * 可以为空，有前端或者客户端自定义
     * 如果要基于该框架实现一个后台模板，可以指定label的值
     * @return
     */
    private String label;
    /**
     * 验证参数
     */
    private List<Annotation> validates;
}
