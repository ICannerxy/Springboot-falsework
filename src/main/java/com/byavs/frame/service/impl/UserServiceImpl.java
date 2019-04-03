package com.byavs.frame.service.impl;

import com.byavs.frame.dao.mapper.SysUserMapper;
import com.byavs.frame.dao.model.SysUser;
import com.byavs.frame.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author XuYang
 * @description: TODO
 * @date 2019/4/313:55
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private SysUserMapper userMapper;

    @Override
    public List<SysUser> listUser() {
        return userMapper.selectByExample(null);
    }
}
