package com.seagetech.web.commons.view.load;

import java.util.List;
import java.util.Map;

/**
 * 自定义增删改查功能
 * @author wangzb
 * @date 2019/12/30 11:03
 * @company 矽甲（上海）信息科技有限公司
 */
public interface IPageViewCustom {
    /**
     * 自定义查询
     * @param viewName 视图名称
     * @param params 查询条件参数
     * @return
     */
    default List<Map<String,Object>> customQuery(String viewName, Map<String, Object> params){
        return null;
    }

    /**
     * 自定义新增功能
     * @param viewName 视图名称
     * @param params 新增内容
     */
    default void customAdd(String viewName, Map<String, Object> params){};

    /**
     * 自定义删除操作
     * @param viewName 视图名称
     * @param params 新增内容
     */
    default void customDeleteById(String viewName, Map<String, Object> params){};
}
