package com.seagetech.web.commons.view.load;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author wangzb
 * @date 2020/1/9 14:38
 * @company 矽甲（上海）信息科技有限公司
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class UserStatusInfo implements IFunctionInfo{
    /**
     * 字段名称
     */
    private String name;

    /**
     * 状态值
     * @return
     */
    private String value;

    /**
     * 状态是否有效，即正常状态，可以登录
     * @return
     */
    private boolean valid;

    /**
     * 非正常状态指定抛出的信息
     * @return
     */
    private String message;

}
