package com.seagetech.web.commons.view.load;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 密码
 * @author wangzb
 * @date 2020/1/9 12:53
 * @company 矽甲（上海）信息科技有限公司
 */
@Data
@NoArgsConstructor
public class PasswordInfo extends DefaultInfo{

    public PasswordInfo(String name, String columnName) {
        super(name, columnName);
    }
}
