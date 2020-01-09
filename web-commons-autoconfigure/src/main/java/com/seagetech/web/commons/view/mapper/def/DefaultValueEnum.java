package com.seagetech.web.commons.view.mapper.def;

import com.seagetech.web.commons.bind.annotation.Add;

import java.util.Objects;

/**
 * 默认值
 * {@link Add#defaultValue()}
 * @author wangzb
 * @date 2020/1/8 15:20
 * @company 矽甲（上海）信息科技有限公司
 */
public enum DefaultValueEnum {
    /**
     * 当前操作时间
     */
    DATE("#date",DateDefaultValue.class),
    /**
     * 当前操作用户
     */
    USER("#user",UserDefaultValue.class),
    /**
     * 默认值 - 密码
     */
    PASSWORD("#password",PasswordDefaultValue.class),
    /**
     * 默认值
     */
    DEFAULT(DefaultValue.class);

    /**
     * 默认值
     */
    private String defaultValue;
    /**
     * 默认值处理类
     */
    private Class<? extends IDefaultValue> defClass;

    DefaultValueEnum(Class<? extends IDefaultValue> defClass) {
        this.defClass = defClass;
    }

    DefaultValueEnum(String defaultValue, Class<? extends IDefaultValue> defClass) {
        this.defaultValue = defaultValue;
        this.defClass = defClass;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public Class<? extends IDefaultValue> getDefClass() {
        return defClass;
    }

    /**
     * 根据默认值获取对应的类
     * @param defaultValue 默认值
     * @return
     */
    public static Class<? extends IDefaultValue> getDefClass(String defaultValue) {
        DefaultValueEnum[] defaultValues = DefaultValueEnum.values();
        for (DefaultValueEnum defaultValueObj : defaultValues){
            if (Objects.equals(defaultValue,defaultValueObj.defaultValue)){
                return defaultValueObj.defClass;
            }
        }
        return DefaultValueEnum.DEFAULT.defClass;
    }


}
