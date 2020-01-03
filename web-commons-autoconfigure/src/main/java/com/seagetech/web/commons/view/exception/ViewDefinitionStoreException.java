package com.seagetech.web.commons.view.exception;

/**
 * view注册失败
 * @author wangzb
 * @date 2019/12/19 9:47
 * @company 矽甲（上海）信息科技有限公司
 */
public class ViewDefinitionStoreException extends RuntimeException {

    /**
     * view组成失败异常
     * @param oldViewDefinition 已经存在的view
     * @param viewDefinition 新的view
     * @param viewName view名称
     */
    public ViewDefinitionStoreException(String oldViewDefinition, String viewDefinition, String viewName) {
        super(" 不能注册" + viewName + ":" + viewDefinition + ",已经存在" + oldViewDefinition);
    }
}
