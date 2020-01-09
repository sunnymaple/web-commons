package com.seagetech.web.commons.view.service.impl;

import com.seagetech.common.util.SeageUtils;
import com.seagetech.web.commons.bind.FunctionType;
import com.seagetech.web.commons.login.session.ISessionHandler;
import com.seagetech.web.commons.view.exception.UniqueException;
import com.seagetech.web.commons.view.load.AddInfo;
import com.seagetech.web.commons.view.load.IFunctionInfo;
import com.seagetech.web.commons.view.load.PageViewContainer;
import com.seagetech.web.commons.view.load.PageViewInfo;
import com.seagetech.web.commons.view.mapper.PageViewMapper;
import com.seagetech.web.commons.view.service.PageViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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

    @Autowired
    private ISessionHandler sessionHandler;

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
        //判重
        List<IFunctionInfo> functionInfos = pageViewInfo.get(FunctionType.ADD);
        functionInfos.forEach(iFunctionInfo -> {
            AddInfo addInfo = (AddInfo) iFunctionInfo;
            if (addInfo.isUnique()){
                String name = addInfo.getName();
                Object value = params.get(name);
                if (!SeageUtils.isEmpty(value)){
                    Map<String,Object> queryParams = new HashMap<>(1);
                    queryParams.put(name,value);
                    List<Map<String, Object>> result = getListByPage(viewName, queryParams);
                    if (result != null && result.size()>0){
                        throw new UniqueException(value + "已存在!");
                    }
                }
            }
        });
        //插入数据
        pageViewMapper.insert(sessionHandler.getUser(),viewName,params);
    }

    @Override
    public void deleteById(String viewName, Integer deleteId) {
        pageViewMapper.deleteById(viewName,deleteId);
    }
}
