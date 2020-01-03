package com.seagetech.web.commons.view.config;

import com.seagetech.common.util.SeageUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.wrapper.MapWrapper;

import java.util.Map;

/**
 * 继承类 MapWrapper,重写findProperty,通过useCamelCaseMapping来判断是否开启使用驼峰
 * @author wangzb
 * @date 2018-11-20 17:43
 * @company 矽甲（上海）信息科技有限公司
 */
public class CustomWrapper extends MapWrapper {
    public CustomWrapper(MetaObject metaObject, Map<String, Object> map) {
        super(metaObject, map);
    }

    /**
     * 通过useCamelCaseMapping来判断是否开启使用驼峰，即是否使用驼峰命名
     * @param name 属性
     * @param useCamelCaseMapping
     * @return
     */
    @Override
    public String findProperty(String name, boolean useCamelCaseMapping) {
        if (useCamelCaseMapping){
            return SeageUtils.lowerCamel(name);
        }
        return name;
    }
}
