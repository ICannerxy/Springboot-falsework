package com.byavs.frame.service;

/**
 * @author XuYang
 * @description:
 * @date 2019/4/8  9:41
 */
public interface RoleService {

    /**
     * 根据角色Id得到唯一角色名称
     *
     * @param roleId
     * @return
     */
    String getSingleRoleName(String roleId);
}
