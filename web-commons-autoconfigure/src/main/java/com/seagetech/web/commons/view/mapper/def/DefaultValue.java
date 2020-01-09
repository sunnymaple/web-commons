package com.seagetech.web.commons.view.mapper.def;

/**
 * 直接返回默认值
 * @author wangzb
 * @date 2020/1/9 9:11
 * @company 矽甲（上海）信息科技有限公司
 */
public class DefaultValue implements IDefaultValue{
    /**
     * 获取默认的值
     *
     * @param userId       当前操作用户主键ID
     * @param name         属性名称
     * @param defaultValue 默认值
     * @return
     */
    @Override
    public String getDefaultValue(Object userId, String name, String defaultValue) {
        return defaultValue;
    }
}
