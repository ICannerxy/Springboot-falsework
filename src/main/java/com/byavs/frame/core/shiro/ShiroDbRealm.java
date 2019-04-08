package com.byavs.frame.core.shiro;

import com.byavs.frame.config.SpringContextHolder;
import com.byavs.frame.core.utli.ToolUtil;
import com.byavs.frame.dao.model.UserProfile;
import com.byavs.frame.service.UserAuthService;
import com.byavs.frame.service.impl.UserAuthServiceServiceImpl;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ShiroDbRealm extends AuthorizingRealm {

    /**
     * 登录认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authToken)
            throws AuthenticationException {
        UserAuthService userAuth = SpringContextHolder.getBean(UserAuthServiceServiceImpl.class);
        UsernamePasswordToken token = (UsernamePasswordToken) authToken;
        UserProfile user = userAuth.user(token.getUsername());
        ShiroUser shiroUser = userAuth.shiroUser(user);
        return userAuth.info(shiroUser, user, super.getName());
    }

    /**
     * 权限认证
     */

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        UserAuthService userAuth = SpringContextHolder.getBean(UserAuthServiceServiceImpl.class);
        ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
        List<String> roleList = shiroUser.getRoleList();
        List<String> roleNameList = shiroUser.getRoleNames();
        Set<String> permissionSet = new HashSet<>();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        for (String roleId : roleList) {
            List<String> permissions = userAuth.findPermissionsByRoleId(roleId);
            if (permissions != null) {
                for (String permission : permissions) {
                    if ( !ToolUtil.isEmpty(permission)) {
                        permissionSet.add(permission);
                    }
                }
            }
        }

        info.addStringPermissions(permissionSet);
        info.addRoles(roleNameList);
        return info;
    }

    /**
     * 设置认证加密方式
     */
    @Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        HashedCredentialsMatcher md5CredentialsMatcher = new HashedCredentialsMatcher();
        md5CredentialsMatcher.setHashAlgorithmName(ShiroKit.hashAlgorithmName);
        md5CredentialsMatcher.setHashIterations(ShiroKit.hashIterations);
        super.setCredentialsMatcher(md5CredentialsMatcher);
    }
}
