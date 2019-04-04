/**
 * Copyright 2018-2020 stylefeng & fengshuonan (https://gitee.com/stylefeng)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.byavs.frame.service.impl;

import cn.hutool.core.convert.Convert;
import com.byavs.frame.dao.mapper.UserProfileMapper;
import com.byavs.frame.dao.model.UserProfile;
import com.byavs.frame.dao.model.UserProfileExample;
import com.byavs.frame.service.UserAuthService;
import com.byavs.frame.core.shiro.ShiroUser;
import com.byavs.frame.core.utli.BeanCopyUtil;
import com.byavs.frame.core.utli.CollectionUtil;
import org.apache.shiro.authc.CredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@DependsOn("springContextHolder")
@Transactional(readOnly = true)
public class UserAuthServiceServiceImpl implements UserAuthService {

    @Autowired
    private UserProfileMapper userMapper;

    /**
     * 根据账号查找用户
     *
     * @param account 账号
     * @return
     */
    @Override
    public UserProfile user(String account) {
        UserProfileExample example = new UserProfileExample();
        UserProfileExample.Criteria criteria = example.createCriteria();
        criteria.andAccountEqualTo(account);
        List<UserProfile> userList = this.userMapper.selectByExample(example);
        // 账号不存在
        if (CollectionUtil.isEmpty(userList)) {
            throw new CredentialsException();
        }
        // 账号被冻结
        /*if (user.getStatus() != ManagerStatus.OK.getCode()) {
            throw new LockedAccountException();
        }*/
        return BeanCopyUtil.cloneObject(userList.get(0), UserProfile.class);
    }

    @Override
    public ShiroUser shiroUser(UserProfile user) {
        ShiroUser shiroUser = new ShiroUser();

        shiroUser.setId(user.getId());
        shiroUser.setAccount(user.getAccount());
        shiroUser.setName(user.getName());
        //Integer[] roleArray = Convert.toIntArray(user.getRoleid());
        List<Integer> roleList = new ArrayList<Integer>();
        List<String> roleNameList = new ArrayList<String>();
       /* for (int roleId : roleArray) {
            roleList.add(roleId);
            roleNameList.add(ConstantFactory.me().getSingleRoleName(roleId));
        }*/
        shiroUser.setRoleList(roleList);
        shiroUser.setRoleNames(roleNameList);

        return shiroUser;
    }

    @Override
    public SimpleAuthenticationInfo info(ShiroUser shiroUser, UserProfile user, String realmName) {
        String credentials = user.getPassword();

        // 密码加盐处理
        String source = user.getSalt();
        ByteSource credentialsSalt = new Md5Hash(source);
        return new SimpleAuthenticationInfo(shiroUser, credentials, credentialsSalt, realmName);
    }

}
