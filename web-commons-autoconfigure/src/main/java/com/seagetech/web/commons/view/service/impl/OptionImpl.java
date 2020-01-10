package com.seagetech.web.commons.view.service.impl;

import com.seagetech.web.commons.view.load.IOption;
import com.seagetech.web.commons.view.load.Option;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author gdl
 * @date 2020/1/9 16:55
 * @company 矽甲（上海）信息科技有限公司
 */
@Component
public class OptionImpl implements IOption{

    private ApplicationContext applicationContext;

    @Override
    public List<Option> getOptions() {
        final OptionImpl bean = applicationContext.getBean(OptionImpl.class);
        return null;
    }


}
