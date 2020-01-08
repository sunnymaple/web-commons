package com.seagetech.web.commons.view.service.impl;

import com.seagetech.web.commons.bind.FunctionType;
import com.seagetech.web.commons.view.load.AddInfo;
import com.seagetech.web.commons.view.load.IFunctionInfo;
import com.seagetech.web.commons.view.load.PageViewContainer;
import com.seagetech.web.commons.view.load.PageViewInfo;
import com.seagetech.web.commons.view.mapper.PageViewMapper;
import com.seagetech.web.commons.view.service.PageViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.*;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;

/**
 * 列表查询业务实现层
 * @author wangzb
 * @date 2019/12/26 9:25
 * @company 矽甲（上海）信息科技有限公司
 */
@Service
public class PageViewServiceImpl implements PageViewService {

    @Autowired
    private PageViewMapper pageViewMapper;

    /**
     * 列表分页查询
     *
     * @param viewName 视图名称
     * @param params   查询条件
     * @return
     */
    @Override
    public List<Map<String,Object>> getListByPage(String viewName, Map<String, Object> params) {
        PageViewContainer pageViewContainer = PageViewContainer.getInstance();
        PageViewInfo pageViewInfo = pageViewContainer.get(viewName);
        boolean enableCustomQuery = pageViewInfo.enableCustomFunction(FunctionType.QUERY);
        if (enableCustomQuery){
            return pageViewInfo.getPageViewCustom().customQuery(viewName,params);
        }
        List<Map<String,Object>> list = pageViewMapper.getList(viewName,params);
        return list;
    }



    /**
     * 添加、新增
     *
     * @param viewName 视图名称
     * @param params   新增内容
     */
    @Override
    public void add(String viewName, Map<String, Object> params) {
        PageViewContainer pageViewContainer = PageViewContainer.getInstance();
        PageViewInfo pageViewInfo = pageViewContainer.get(viewName);
        boolean enableCustomAdd = pageViewInfo.enableCustomFunction(FunctionType.ADD);
        if (enableCustomAdd){
            //自定义方法
            pageViewInfo.getPageViewCustom().customAdd(viewName,params);
        }
        //
    }



    @Override
    public void deleteById(String viewName, Integer deleteId) {
        pageViewMapper.deleteById(viewName,deleteId);
    }
}
