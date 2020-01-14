package com.seagetech.web.commons.view.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.seagetech.web.commons.view.entity.BasedRolePermission;

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
public interface IBasedRolePermissionService extends IService<BasedRolePermission> {
    /**
     * 根据角色获取权限
     * @param roleIds
     * @return
     */
    default List<Integer> getPermissionsByRoles(List<Integer> roleIds){
        return list(new LambdaQueryWrapper<BasedRolePermission>()
                .in(BasedRolePermission::getRoleId,roleIds))
                .stream()
                .map(BasedRolePermission::getPermissionId)
                .collect(Collectors.toList());
    }
}
