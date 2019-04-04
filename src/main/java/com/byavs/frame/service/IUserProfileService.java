package com.byavs.frame.service;

import com.byavs.frame.domain.vo.UserProfileVo;

/**
 * @author XuYang
 * @description:
 * @date 2019/4/414:12
 */
public interface IUserProfileService {

    /**
     * 添加用户
     *
     * @param userProfileVo
     */
    void addUser(UserProfileVo userProfileVo);
}
