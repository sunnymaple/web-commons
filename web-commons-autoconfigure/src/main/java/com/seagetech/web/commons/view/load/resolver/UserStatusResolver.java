package com.seagetech.web.commons.view.load.resolver;

import com.seagetech.web.commons.bind.annotation.UserStatus;
import com.seagetech.web.commons.view.load.UserStatusInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户状态标记
 * @author wangzb
 * @date 2020/1/9 14:30
 * @company 矽甲（上海）信息科技有限公司
 */
public class UserStatusResolver extends AbstractResolver<UserStatus, UserStatusInfo> {

    /**
     * 解析
     *
     * @return R
     */
    @Override
    public List<UserStatusInfo> resolver() {
        String name = field.getName();
        List<UserStatusInfo> results = new ArrayList<>(1);
        results.add(UserStatusInfo
                .builder()
                .build()
                .setName(name)
                .setValue(annotation.value())
                .setMessage(annotation.message())
                .setValid(annotation.valid()));
        return results;
    }
}
