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
package com.byavs.frame.service;

import com.byavs.frame.core.shiro.ShiroUser;
import com.byavs.frame.dao.model.UserProfile;
import org.apache.shiro.authc.SimpleAuthenticationInfo;

/**
 * 定义shirorealm所需数据的接口
 *
 * @author fengshuonan
 * @date 2016年12月5日 上午10:23:34
 */
public interface UserAuthService {

    /**
     * 根据账号获取登录用户
     *
     * @param account 账号
     */
    UserProfile user(String account);


    /**
     * 根据系统用户获取Shiro的用户
     *
     * @param user 系统用户
     */
    ShiroUser shiroUser(UserProfile user);

    /**
     * 获取shiro的认证信息
     */
    SimpleAuthenticationInfo info(ShiroUser shiroUser, UserProfile user, String realmName);

}
