package com.seagetech.web.commons.view.load.resolver;

import com.seagetech.common.util.SeageUtils;
import com.seagetech.web.commons.bind.annotation.Password;
import com.seagetech.web.commons.view.load.PasswordInfo;

import java.util.Arrays;
import java.util.List;

/**
 * @author wangzb
 * @date 2020/1/9 12:00
 * @company 矽甲（上海）信息科技有限公司
 */
public class PasswordResolver extends AbstractResolver<Password, PasswordInfo>{
    /**
     * 解析
     *
     * @return R
     */
    @Override
    public List<PasswordInfo> resolver() {
        //字段名称，为指定时使用列名
        String fieldName = field.getName();
        //列名称
        String columnName = SeageUtils.isEmpty(annotation.columnName()) ?
                SeageUtils.upperUnderScore(fieldName) : annotation.columnName();
        return Arrays.asList(new PasswordInfo(fieldName,columnName));
    }
}
