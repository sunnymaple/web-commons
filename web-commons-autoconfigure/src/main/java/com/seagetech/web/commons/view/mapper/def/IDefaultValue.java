package com.seagetech.web.commons.view.mapper.def;

/**
 * @author wangzb
 * @date 2020/1/8 17:16
 * @company 矽甲（上海）信息科技有限公司
 */
public interface IDefaultValue {
    /**
     * 获取默认的值
     * @param userId 当前操作用户主键ID
     * @param name 属性名称
     * @param defaultValue 默认值
     * @return
     */
    String getDefaultValue(Object userId,String name,String defaultValue);
}
