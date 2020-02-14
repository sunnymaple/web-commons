package com.seagetech.web.commons.view.controller;

import com.seagetech.common.util.SeageUtils;
import com.seagetech.web.bind.PageHandlerType;
import com.seagetech.web.bind.annotation.PageHandler;
import com.seagetech.web.commons.util.Utils;
import com.seagetech.web.commons.view.load.Option;
import com.seagetech.web.commons.view.load.PageViewContainer;
import com.seagetech.web.commons.view.load.PageViewInfo;
import com.seagetech.web.commons.view.service.PageViewService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.AbstractResource;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

/**
 * 用于后台管理系统列表页面的增删改查
 * @author wangzb
 * @date 2019/12/19 9:08
 * @company 矽甲（上海）信息科技有限公司
 */
@RestController
@RequestMapping("/view")
@Validated
public class PageViewController {

    @Autowired
    private PageViewService pageViewService;

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;

    /**
     * 前端视图
     * @param viewName
     * @return
     */
    @GetMapping("/{viewName}")
    @RequiresPermissions("view")
    @PageHandler
    public ModelAndView view(@PathVariable(value = "viewName") String viewName, ModelMap modelMap){
        PageViewContainer pageViewContainer = PageViewContainer.getInstance();
        PageViewInfo pageViewInfo = pageViewContainer.get(viewName);
        String viewPath = SeageUtils.isEmpty(pageViewInfo.getViewPath()) ? viewName : pageViewInfo.getViewPath();
        modelMap.put("viewName",viewName);
        modelMap.put("primaryKey",pageViewInfo.getPrimaryKey().getName());
        return new ModelAndView(viewPath);
    }

    /**
     * 列表分页查询
     * @param viewName 视图名称
     * @return
     */
    @GetMapping("/getListByPage/{viewName}")
    @PageHandler(pageHandlerType = PageHandlerType.NOT_PAGE)
    @RequiresPermissions("view")
    public List<Map<String,Object>> getListByPage(@PathVariable(value = "viewName") String viewName){
        return pageViewService.getList(viewName, Utils.getParameter(request));
    }

    /**
     * 添加、新增
     * @param viewName 视图名称
     * @return
     */
    @RequestMapping(value = "/add/{viewName}",produces = "application/json")
    @ParamValidated
    @RequiresPermissions("view")
    public String add(@PathVariable(value = "viewName") String viewName) {
        pageViewService.add(viewName,Utils.getParameter(request));
        return "添加成功!";
    }

    /**
     *
     * @param viewName 视图名称
     * @return
     */
    @RequestMapping(value = "/update/{viewName}",produces = "application/json")
    @ParamValidated
    @RequiresPermissions("view")
    public String update(@PathVariable(value = "viewName") String viewName) {
        pageViewService.update(viewName,Utils.getParameter(request));
        return "修改成功!";
    }


    /**
     * 删除操作
     * @param viewName
     * @param id 主键ID值
     */
    @GetMapping("/delete/{viewName}")
    @RequiresPermissions("view")
    public String deleteById(@PathVariable(value = "viewName") String viewName,
                           @NotBlank(message = "主键值不能为空！") String id, String status){
        String[] idArr = id.split(",");
        pageViewService.deleteById(viewName, idArr,status);
        return "删除成功！";
    }

    /**
     * 文件导入
     * @param viewName 视图名称
     * @param multipartFile 导入文件文件
     */
    @PostMapping("/import/{viewName}")
    @RequiresPermissions("view")
    public String importTable(@PathVariable(value = "viewName") String viewName, @RequestParam("file") MultipartFile multipartFile) throws Exception {
        pageViewService.importTable(viewName,multipartFile);
        return "导入成功";
    }

    /**
     * 模板下载
     * @para
     * m  viewName 视图名称
     */
    @GetMapping("/template/{viewName}")
    public ResponseEntity<AbstractResource> getTemplate(@PathVariable(value = "viewName") String viewName)throws Exception{
        return pageViewService.getTemplate(viewName);
    }

    /**
     * 导出
     * @param viewName 视图名称
     * @throws Exception
     */
    @GetMapping("/export/{viewName}")
    @RequiresPermissions("view")
    public void exportExcel(@PathVariable(value = "viewName") String viewName) throws Exception{
        pageViewService.exportExcel(viewName, Utils.getParameter(request),request,response);
    }

    /**
     * 获取下拉选项
     * @param viewName 视图名称
     * @return
     */
    @GetMapping("/getSelectOptions/{viewName}")
    public List<Option> getSelectOptions(@PathVariable(value = "viewName") String viewName){
        return pageViewService.getOptions(viewName,Utils.getParameter(request));
    }
}
