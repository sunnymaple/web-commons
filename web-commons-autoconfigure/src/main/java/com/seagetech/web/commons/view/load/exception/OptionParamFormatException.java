package com.seagetech.web.commons.view.load.exception;

/**
 * 选项参数格式不对异常
 * @author wangzb
 * @date 2020/1/19 18:31
 * @company 矽甲（上海）信息科技有限公司
 */
public class OptionParamFormatException extends PageViewException{

    private String optionParam;

    public OptionParamFormatException(String viewName,String optionParam) {
        super(viewName,"参数" + optionParam + "格式不正确，请使用key=value的形式");
    }

    public String getOptionParam() {
        return optionParam;
    }
}
