package com.seagetech.web.commons.bind;

/**
 * 查询条件连接符
 * @author wangzb
 * @date 2019/12/26 13:46
 * @company 矽甲（上海）信息科技有限公司
 */
public enum Condition {
    /**
     * =
     */
    EQ("="),
    /**
     * 不等于
     */
    NOT_EQ("<>"),
    /**
     * 大于
     */
    GT(">"),
    /**
     * 大于等于
     */
    GT_EQ(">="),
    /**
     * 小于
     */
    LT("<"),
    /**
     * 小于等于
     */
    LT_EQ("<="),
    /**
     *
     */
    LIKE("like");

    Condition(String condition) {
        this.condition = condition;
    }

    private String condition;

    public String getCondition() {
        return condition;
    }
}
