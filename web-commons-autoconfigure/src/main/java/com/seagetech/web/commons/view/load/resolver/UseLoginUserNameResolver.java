package com.seagetech.web.commons.view.load.resolver;

import com.seagetech.common.util.SeageUtils;
import com.seagetech.web.commons.bind.annotation.UseLoginUserName;
import com.seagetech.web.commons.view.load.UseLoginUserNameInfo;

import java.util.Arrays;
import java.util.List;

/**
 * 用于登录字段标记的{@link UseLoginUserName }解析
 * @author wangzb
 * @date 2020/1/9 14:00
 * @company 矽甲（上海）信息科技有限公司
 */
public class UseLoginUserNameResolver extends AbstractResolver<UseLoginUserName, UseLoginUserNameInfo>{
    /**
     * 解析
     *
     * @return R
     */
    @Override
    public List<UseLoginUserNameInfo> resolver() {
        //字段名称，为指定时使用列名
        String fieldName = field.getName();
        //列名称
        String columnName = SeageUtils.isEmpty(annotation.columnName()) ?
                SeageUtils.upperUnderScore(fieldName) : annotation.columnName();
        return Arrays.asList(new UseLoginUserNameInfo(fieldName,columnName));
    }
}
