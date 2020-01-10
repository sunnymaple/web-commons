package com.seagetech.web.commons.view.mapper.def;

import com.seagetech.web.commons.login.shiro.ShiroUtils;
import com.seagetech.web.commons.view.load.PageViewProperties;

/**
 * 密码默认值
 * @author wangzb
 * @date 2020/1/8 17:19
 * @company 矽甲（上海）信息科技有限公司
 */
public class PasswordDefaultValue implements IDefaultValue{

    private PageViewProperties pageViewProperties = PageViewProperties.getInstance();

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
        return ShiroUtils.encryption(pageViewProperties.getDefaultPassword(),userId.toString());
    }
}
