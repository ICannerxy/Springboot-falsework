package com.byavs.frame.service;

import com.byavs.frame.dao.mapper.UserAdminFunPermMapper;
import com.byavs.frame.dao.model.UserAdminFunPerm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author XuYang
 * @description: TODO
 * @date 2019/3/218:59
 */
@Service
public class UserAdminFunPermService {
    @Autowired
    private UserAdminFunPermMapper funPermMapper;

    public List<UserAdminFunPerm> list2() {
        List<UserAdminFunPerm> userAdminFunPerms = funPermMapper.selectByExample(null);
        System.out.println(userAdminFunPerms);
        return userAdminFunPerms;
    }
}
