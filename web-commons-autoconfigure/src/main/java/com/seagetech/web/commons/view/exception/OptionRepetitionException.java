package com.seagetech.web.commons.view.exception;

import com.seagetech.web.commons.bind.OptionType;

/**
 *  Option选项类型重复异常
 * @author wangzb
 * @date 2020/1/19 15:22
 * @company 矽甲（上海）信息科技有限公司
 */
public class OptionRepetitionException extends RuntimeException{
    /**
     * 视图名称
     */
    private String viewName;

    private OptionType optionType;

    /**
     *
     */
    public OptionRepetitionException( String viewName, OptionType optionType) {
        super(viewName + "视图的" + optionType + "重复");
        this.viewName = viewName;
        this.optionType = optionType;
    }

    public String getViewName() {
        return viewName;
    }

    public OptionType getOptionType() {
        return optionType;
    }
}
