package com.byavs.frame.service.impl;

import com.byavs.frame.dao.mapper.RoleMapper;
import com.byavs.frame.dao.model.Role;
import com.byavs.frame.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author XuYang
 * @description:
 * @date 2019/4/8  9:43
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;
    /**
     * 根据角色Id得到唯一角色名称
     *
     * @param roleId
     * @return
     */
    @Override
    public String getSingleRoleName(String roleId) {
        Role role = roleMapper.selectByPrimaryKey(roleId);
        return null == role ? "" : role.getName();
    }
}
