package com.seagetech.web.commons.bind;

/**
 * 排序
 * @author wangzb
 * @date 2020/1/19 9:35
 * @company 矽甲（上海）信息科技有限公司
 */
public enum SortType {
    /**
     * 升序
     */
    ASC("ASC"),
    /**
     * 降序
     */
    DESC("DESC"),
    /**
     * none不排序，默认方式
     */
    NONE("NONE"),
    ;

    private String sort;

    SortType(String sort) {
        this.sort = sort;
    }

    public String getSort() {
        return sort;
    }
}
