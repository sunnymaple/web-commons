package com.seagetech.web.commons.view.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.seagetech.web.commons.view.entity.BasedUserRole;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wangzb
 * @since 2020-01-13
 */
public interface IBasedUserRoleService extends IService<BasedUserRole> {

    /**
     * 获取用户角色
     * @param userId 用户主键ID
     * @return
     */
    default List<Integer> getUserRoles(Integer userId) {
        return list(new LambdaQueryWrapper<BasedUserRole>()
                .eq(BasedUserRole::getUserId,userId))
                .stream()
                .map(BasedUserRole::getRoleId)
                .collect(Collectors.toList());
    }
}
