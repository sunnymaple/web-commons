package com.seagetech.web.commons.view.load;

import com.seagetech.web.commons.view.load.exception.ViewNameNotFountException;

import java.util.HashMap;

/**
 * View视图容器
 * 用于存放类视图实体类
 * @author wangzb
 * @date 2019/12/19 9:26
 * @company 矽甲（上海）信息科技有限公司
 */
public class PageViewContainer extends HashMap<String,PageViewInfo> {

    /**
     * 定义
     */
    private static volatile PageViewContainer viewContainer;


    /**
     * Creates a new, empty map with the default initial table size (16).
     */
    private PageViewContainer() {
    }

    /**
     * 获取实例
     * @return
     */
    public static PageViewContainer getInstance(){
        if (viewContainer == null){
            synchronized (PageViewContainer.class){
                if (viewContainer == null){
                    viewContainer = new PageViewContainer();
                }
            }
        }
        return viewContainer;
    }

    /**
     * 重写get方法，获取不到抛出异常
     * @param key
     * @return
     * @throws {@link ViewNameNotFountException} 当key不存在时抛出该异常
     */
    @Override
    public PageViewInfo get(Object key) {
        PageViewInfo pageViewInfo = super.get(key);
        if (pageViewInfo == null){
            throw new ViewNameNotFountException(key.toString());
        }
        return pageViewInfo;
    }
}
