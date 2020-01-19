package com.seagetech.web.commons.view.load;

import com.seagetech.web.commons.bind.Condition;
import com.seagetech.web.commons.bind.SortType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 查询
 * @author wangzb
 * @date 2019/12/27 9:39
 * @company 矽甲（上海）信息科技有限公司
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryInfo implements IFunctionInfo {
    /**
     * 查询条件连接符
     * {@link Condition}
     */
    private Condition condition;
    /**
     * 参数名称
     */
    private String name;
    /**
     * 列表名称
     */
    private String columnName;
    /**
     * 页面查询条件的label的名称
     * 可以为空，有前端或者客户端自定义
     * 如果要基于该框架实现一个后台模板，可以指定label的值
     */
    private String label;
    /**
     * 只查询的值
     * 比如只查询出状态为1的，0表示被删除的不显示出来
     */
    private String[] queryOnly;
    /**
     * 不查询出的值
     */
    private String[] notQuery;
    /**
     * 排序
     */
    private SortType sortType;
}
