package com.seagetech.web.commons.view.controller;

import com.seagetech.common.exception.ParamException;
import com.seagetech.common.util.SeageJson;
import com.seagetech.web.bind.PageHandlerType;
import com.seagetech.web.bind.annotation.PageHandler;
import com.seagetech.web.commons.util.Utils;
import com.seagetech.web.commons.view.load.PageViewContainer;
import com.seagetech.web.commons.view.load.PageViewInfo;
import com.seagetech.web.commons.view.service.PageViewService;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    public ModelAndView view(@PathVariable(value = "viewName") String viewName){
        PageViewContainer pageViewContainer = PageViewContainer.getInstance();
        PageViewInfo pageViewInfo = pageViewContainer.get(viewName);
        return new ModelAndView(Optional.ofNullable(pageViewInfo.getViewPath()).orElse(viewName));
    }

    /**
     * 列表分页查询
     * @param viewName 视图名称
     * @return
     */
    @GetMapping("/getListByPage/{viewName}")
    @PageHandler(pageHandlerType = PageHandlerType.NOT_PAGE)
    public List<Map<String,Object>> getListByPage(@PathVariable(value = "viewName") String viewName){
        return pageViewService.getListByPage(viewName, Utils.getParameter(request));
    }

    /**
     * 添加、新增
     * @param viewName 视图名称
     * @return
     */
    @RequestMapping(value = "/add/{viewName}",produces = "application/json")
    @ParamValidated
    public String add(@PathVariable(value = "viewName") String viewName) {
        pageViewService.add(viewName,Utils.getParameter(request));
        return "添加成功!";
    }

    /**
     * 删除操作
     * @param viewName
     * @param deleteId
     */
    @GetMapping("/deleteById/{viewName}/{deleteId}")
    public void deleteById(@PathVariable(value = "viewName") String viewName,@PathVariable(value = "deleteId") Integer deleteId){
        pageViewService.deleteById(viewName, deleteId);
    }

    /**
     * 文件导入
     * @param viewName 视图名称
     * @param dataPic 导入文件文件
     */
    @PostMapping("/importTable/{viewName}")
    public String importTable(@PathVariable(value = "viewName") String viewName, MultipartFile dataPic) throws Exception {
        pageViewService.importTable(viewName,dataPic,request);
        return "导入成功";
    }

    /**
     * 模板下载
     * @param filePath 文件所在文件夹名称
     * @param excelName 模板名称
     */
    @GetMapping("/excelFormWork/{filePath}/{excelName}")
    public void excelFormWork(@PathVariable(value = "filePath") String filePath,@PathVariable(value = "excelName") String excelName)throws Exception{
        pageViewService.excelFormWork(filePath,excelName,request,response);
    }
}
