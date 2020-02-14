package com.seagetech.web.commons.view.load.resolver;

import com.seagetech.common.util.SeageUtils;
import com.seagetech.web.commons.bind.FunctionType;
import com.seagetech.web.commons.bind.annotation.Option;
import com.seagetech.web.commons.view.exception.OptionRepetitionException;
import com.seagetech.web.commons.view.load.IFunctionInfo;
import com.seagetech.web.commons.view.load.OptionInfo;
import com.seagetech.web.commons.view.load.PageViewContainer;
import com.seagetech.web.commons.view.load.PageViewInfo;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 选项解析器
 * @author wangzb
 * @date 2020/1/19 15:08
 * @company 矽甲（上海）信息科技有限公司
 */
public class OptionResolver extends AbstractResolver<Option, OptionInfo>{
    /**
     * 解析
     *
     * @return R
     */
    @Override
    public List<OptionInfo> resolver() {
        PageViewContainer pageViewContainer = PageViewContainer.getInstance();
        PageViewInfo pageViewInfo = pageViewContainer.get(viewName);
        List<IFunctionInfo> options = pageViewInfo.get(FunctionType.OPTION);
        if (!SeageUtils.isEmpty(options)){
            for (IFunctionInfo option : options){
                OptionInfo optionInfo = (OptionInfo) option;
                if (Objects.equals(optionInfo.getOptionType(),annotation.value())){
                    throw new OptionRepetitionException(viewName,optionInfo.getOptionType());
                }
            }
        }
        return Arrays.asList(OptionInfo.builder().build()
                .setName(field.getName())
                .setColumnName(SeageUtils.upperUnderScore(field.getName()))
                .setOptionType(annotation.value()));
    }
}
