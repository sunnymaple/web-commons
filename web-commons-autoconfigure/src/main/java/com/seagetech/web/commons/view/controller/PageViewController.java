package com.seagetech.web.commons.view.controller;

import com.seagetech.web.bind.annotation.PageHandler;
import com.seagetech.web.commons.util.Utils;
import com.seagetech.web.commons.view.service.PageViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
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
public class PageViewController {

    @Autowired
    private PageViewService pageViewService;

    @Autowired
    private HttpServletRequest request;

    /**
     *
     * @param viewName
     * @return
     */
    @GetMapping("/{viewName}")
    public ModelAndView view(@PathVariable(value = "viewName") String viewName, ModelMap modelMap){
        return null;
    }

    /**
     * 列表分页查询
     * @param viewName 视图名称
     * @return
     */
    @GetMapping("/getListByPage/{viewName}")
    @PageHandler
    public List<Map<String,Object>> getListByPage(@PathVariable(value = "viewName") String viewName){
        return pageViewService.getListByPage(viewName, Utils.getParameter(request));
    }

}
