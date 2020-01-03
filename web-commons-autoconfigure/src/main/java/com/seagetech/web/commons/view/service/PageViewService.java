package com.seagetech.web.commons.view.service;

import java.util.List;
import java.util.Map;

/**
 * 列表查询业务层
 * @author wangzb
 * @date 2019/12/26 9:24
 * @company 矽甲（上海）信息科技有限公司
 */
public interface PageViewService {

    /**
     * 列表分页查询
     * @param viewName 视图名称
     * @param params 查询条件
     * @return
     */
    List<Map<String,Object>> getListByPage(String viewName, Map<String,Object> params);
}
