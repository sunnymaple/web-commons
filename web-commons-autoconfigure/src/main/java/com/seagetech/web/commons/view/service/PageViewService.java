package com.seagetech.web.commons.view.service;

import com.seagetech.web.commons.view.load.Option;
import org.springframework.core.io.AbstractResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    List<Map<String,Object>> getList(String viewName, Map<String,Object> params);

    /**
     * 添加、新增
     * @param viewName 视图名称
     * @param params 新增内容
     */
    void add(String viewName,Map<String,Object> params);

    /**
     * 修改功能
     * @param viewName 视图名称
     * @param params 修改内容
     */
    void update(String viewName,Map<String,Object> params);

    /**
     * 根据主键删除数据
     * @param viewName 视图名称
     * @param ids 删除主键ID
     * @param status 如果是逻辑删除需要传递值
     *              如启用、禁用、删除，通过状态去控制
     */
    void deleteById(String viewName, String[] ids,String status);

    /**
     * 导入文件
     * @param viewName 视图名称
     * @param multipartFile 文件
     * @throws Exception
     */
    void importTable(String viewName, MultipartFile multipartFile) throws Exception;

    /**
     * 模板下载
     * @param viewName 视图名称
     * @throws Exception
     */
    ResponseEntity<AbstractResource> getTemplate(String viewName) throws Exception;

    /**
     * 导出文件
     * @param viewName 视图名称
     * @param parameter 参数
     * @param request
     * @param response
     * @throws Exception
     */
    void exportExcel(String viewName, Map<String, Object> parameter, HttpServletRequest request, HttpServletResponse response) throws Exception;

    /**
     * 获取下拉选项
     * @param viewName 视图名称
     * @param params 请求参数
     * @return
     */
    List<Option> getOptions(String viewName,Map<String,Object> params);
}
