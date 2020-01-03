package com.seagetech.web.commons.view.config;

import com.seagetech.common.util.SeageUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.wrapper.ObjectWrapper;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;

import java.util.Map;

/**
 * 实现接口 ObjectWrapperFactory,通过包装工厂来创建自定义的包装类,
 * 通过hasWrapperFor判断参数不为空,并且类型是Map的时候才使用自己扩展的ObjectWrapper
 * @author wangzb
 * @date 2018-11-20 17:52
 * @company 矽甲（上海）信息科技有限公司
 */
public class MapWrapperFactory implements ObjectWrapperFactory {
    @Override
    public boolean hasWrapperFor(Object o) {
        //参数不为空，且为Map时返回true
        return !SeageUtils.isEmpty(o) && o instanceof Map;
    }

    @Override
    public ObjectWrapper getWrapperFor(MetaObject metaObject, Object o) {
        return new CustomWrapper(metaObject,(Map)o);
    }
}
