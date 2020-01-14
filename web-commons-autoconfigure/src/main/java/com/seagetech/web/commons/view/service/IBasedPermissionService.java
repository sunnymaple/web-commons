package com.seagetech.web.commons.view.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.seagetech.web.commons.view.entity.BasedPermission;

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
public interface IBasedPermissionService extends IService<BasedPermission> {
    /**
     * 获取用户菜单
     * @param permissionIds
     * @return
     */
    default List<Integer> getMenusByPermission(List<Integer> permissionIds){
        return list(new LambdaQueryWrapper<BasedPermission>()
                .in(BasedPermission::getPermissionId)
                .eq(BasedPermission::getPermissionType,1))
                .stream()
                .map(BasedPermission::getMenuId)
                .collect(Collectors.toList());
    }

}
