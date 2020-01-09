package com.seagetech.web.commons.view.mapper.def;

/**
 * @author wangzb
 * @date 2020/1/8 17:44
 * @company 矽甲（上海）信息科技有限公司
 */
public class UserDefaultValue implements IDefaultValue{
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
        return userId.toString();
    }
}
