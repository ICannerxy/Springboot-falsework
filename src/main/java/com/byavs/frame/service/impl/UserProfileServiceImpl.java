package com.byavs.frame.service.impl;

import com.byavs.frame.core.exception.ApplicationException;
import com.byavs.frame.core.shiro.ShiroKit;
import com.byavs.frame.core.utli.BeanCopyUtil;
import com.byavs.frame.core.utli.ToolUtil;
import com.byavs.frame.dao.mapper.UserProfileMapper;
import com.byavs.frame.dao.model.UserProfile;
import com.byavs.frame.dao.model.UserProfileExample;
import com.byavs.frame.domain.vo.UserProfileVo;
import com.byavs.frame.service.IUserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author XuYang
 * @description:
 * @date 2019/4/4 14:17
 */
@Service
public class UserProfileServiceImpl implements IUserProfileService {

    @Autowired
    private UserProfileMapper userProfileMapper;

    /**
     * 添加用户
     *
     * @param userProfileVo
     */
    @Override
    public void addUser(UserProfileVo userProfileVo) {
        // 基础检查
        checkBase(userProfileVo);
        // 检查账户是否存在
        checkAccount(userProfileVo.getAccount());
        // 完善账号信息
        userProfileVo.setSalt(ShiroKit.getRandomSalt(5));
        userProfileVo.setPassword(ShiroKit.md5(userProfileVo.getPassword(), userProfileVo.getSalt()));
        UserProfile userProfile = BeanCopyUtil.cloneObject(userProfileVo, UserProfile.class);
        userProfile.setDataForInsert();
        userProfileMapper.insertSelective(userProfile);
    }

    /**
     * 检查账户是否存在
     *
     * @param account
     */
    private void checkAccount(String account) {
        UserProfileExample example = new UserProfileExample();
        UserProfileExample.Criteria criteria = example.createCriteria();
        criteria.andAccountEqualTo(account);
        long count = this.userProfileMapper.countByExample(example);
        if (count > 0) {
            throw new ApplicationException("该用户已存在!");
        }
    }


    /**
     * 基础检查
     *
     * @param userProfileVo
     */
    private void checkBase(UserProfileVo userProfileVo) {
        StringBuilder builder = new StringBuilder();
        if (ToolUtil.isEmpty(userProfileVo.getAccount())) {
            builder.append(",用户名不能为空");
        }
        if (ToolUtil.isEmpty(userProfileVo.getPassword())) {
            builder.append(",密码不能为空");
        }
        if (builder.length() > 0) {
            throw new ApplicationException(builder.toString());
        }
    }
}
