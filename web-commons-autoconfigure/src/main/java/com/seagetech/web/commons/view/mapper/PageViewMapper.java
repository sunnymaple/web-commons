package com.seagetech.web.commons.view.mapper;

import com.seagetech.web.commons.view.mapper.provider.DynamicSelectProvider;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

/**
 * 持久层
 * @author wangzb
 * @date 2019/12/26 9:49
 * @company 矽甲（上海）信息科技有限公司
 */
public interface PageViewMapper {
    /**
     * 列表条件查询
     * @param viewName 视图名称
     * @param params 条件查询
     * @return
     */
    @SelectProvider(type = DynamicSelectProvider.class, method = "createSql")
    List<Map<String,Object>> getList(String viewName,Map<String, Object> params);
}
