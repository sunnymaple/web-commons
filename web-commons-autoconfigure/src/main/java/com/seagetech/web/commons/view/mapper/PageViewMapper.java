package com.seagetech.web.commons.view.mapper;

import com.seagetech.web.commons.view.mapper.provider.*;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
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

    /**
     * 新增
     * @param userId 用户主键
     * @param viewName 视图名称
     * @param params 条件查询
     */
    @InsertProvider(type = DynamicInsertProvider.class, method = "createSql")
    void insert(Object userId,String viewName,Map<String, Object> params);

    @UpdateProvider(type = DynamicUpdateProvider.class,method = "createSql")
    void update(Object userId,String viewName,Map<String, Object> params);

    /**
     * 根据主键删除
     * @param viewName 视图名称
     * @param ids 删除主键ID
     * @param status
     */
    @DeleteProvider(type = DynamicDeleteProvider.class,method = "deleteById")
    void deleteById(String viewName, String[] ids,String status);

    /**
     * 导入文件插入数据库
     * @param sqlMap 插入数据
     */
    @InsertProvider(type = DynamicImportProvider.class, method = "batchImport")
    void importTable(List<Map<String,Object>> sqlMap,String tableName);
}
