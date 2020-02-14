package com.seagetech.web.commons.bind;

/**
 * 功能类型
 * @author wangzb
 * @date 2019/12/27 16:18
 * @company 矽甲（上海）信息科技有限公司
 */
public enum FunctionType {
    /**
     * 查询
     */
    QUERY,
    /**
     * 新增
     */
    ADD,
    /**
     * 删除
     */
    DELETE,
    /**
     * 更新
     */
    UPDATE,
    /**
     * 导入
     */
    IMPORT,
    /**
     * 导出
     */
    EXPORT,
    /**
     * 主键
     */
    PRIMARY_KEY,
    /**
     * 用于登录的用户名字段
     */
    USE_LOGIN_USER_NAME,
    /**
     * 密码字段
     */
    PASSWORD,
    /**
     * 用户状态标记
     */
    USER_STATUS,
    /**
     * 选项
     */
    OPTION;
}
