package com.seagetech.web.commons.view.load;

import com.seagetech.web.commons.view.DefaultViewName;
import com.seagetech.web.commons.view.service.PageViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author wangzb
 * @date 2020/1/19 16:37
 * @company 矽甲（上海）信息科技有限公司
 */
@Component
public class DefaultOption implements IOption{

    @Autowired
    private PageViewService pageViewService;

    /**
     * 获取选项
     *
     * @param params 查询条件
     * @return
     */
    @Override
    public List<Option> getOptions(Map<String, Object> params) {
        return pageViewService.getOptions(DefaultViewName.DICTIONARY,params);
    }
}
