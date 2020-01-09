package com.seagetech.web.commons.view.mapper.def;

import com.seagetech.common.util.DateUtils;

import java.util.Date;

/**
 * 默认值 - 当前日期生成策略
 * @author wangzb
 * @date 2020/1/8 17:46
 * @company 矽甲（上海）信息科技有限公司
 */
public class DateDefaultValue implements IDefaultValue {
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
        Date date = new Date();
        return DateUtils.format(date,defaultValue.substring(DefaultValueEnum.DATE.getDefaultValue().length() + 2));
    }
}
