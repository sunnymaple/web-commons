package com.seagetech.web.commons.view.load;

import java.util.List;
import java.util.Map;

/**
 * 导入时可能用到的选项
 * @author gdl
 * @date 2020/1/9 15:23
 * @company 矽甲（上海）信息科技有限公司
 */
public interface IOption {

    /**
     * 获取选项
     * @param params 查询条件
     * @return
     */
    List<Option> getOptions(Map<String,Object> params);


}
