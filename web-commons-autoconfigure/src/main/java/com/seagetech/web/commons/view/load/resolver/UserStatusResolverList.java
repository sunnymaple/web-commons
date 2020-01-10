package com.seagetech.web.commons.view.load.resolver;

import com.seagetech.web.commons.bind.annotation.Query;
import com.seagetech.web.commons.bind.annotation.UserStatus;
import com.seagetech.web.commons.view.load.UserStatusInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangzb
 * @date 2020/1/9 18:00
 * @company 矽甲（上海）信息科技有限公司
 */
public class UserStatusResolverList extends AbstractResolver<UserStatus.List, UserStatusInfo>{
    /**
     * 解析
     *
     * @return R
     */
    @Override
    public List<UserStatusInfo> resolver() {
        UserStatus[] values = annotation.value();
        String name = field.getName();
        List<UserStatusInfo> results = new ArrayList<>(values.length);
        for (UserStatus userStatus : values){
            results.add(UserStatusInfo
                    .builder()
                    .build()
                    .setName(name)
                    .setValue(userStatus.value())
                    .setMessage(userStatus.message())
                    .setValid(userStatus.valid()));
        }
        return results;
    }
}
