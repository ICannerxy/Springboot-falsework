package com.byavs.frame.controller;

import com.byavs.frame.core.entity.Response;
import com.byavs.frame.core.shiro.ShiroKit;
import com.byavs.frame.core.shiro.ShiroUser;
import com.byavs.frame.core.utli.HttpContext;
import com.byavs.frame.domain.vo.UserProfileVo;
import com.byavs.frame.service.IUserProfileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;

/**
 * @author XuYang
 * @description: 登录控制器
 * @date 2019/4/313:31
 */
@Api(description = "登录模块")
@RestController
public class LoginController  {

    @Autowired
    private IUserProfileService userProfileService;

    /**
     * 添加用户
     *
     * @param userProfileVo
     * @return
     */
    @PostMapping("/addUser")
    @ApiOperation("添加用户")
    public Response addUser(@RequestBody UserProfileVo userProfileVo) {
        userProfileService.addUser(userProfileVo);
        return Response.success(userProfileVo.getId());
    }

    /**
     * 点击登录执行的动作
     */
    @ApiOperation("用户登录")
    @PostMapping(value = "/login")
    public Response loginValidate(@ApiParam(value = "用户名",required = true)@RequestParam("username") String username,
                                  @ApiParam(value = "密码",required = true)@RequestParam("password")String password,
                                  @ApiParam("记住我")@RequestParam(value = "rememberMe",required = false)boolean rememberMe) {
        Subject currentUser = ShiroKit.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password.toCharArray());
        if (rememberMe)
            token.setRememberMe(true);
        else
            token.setRememberMe(false);
        currentUser.login(token);
        ShiroUser shiroUser = ShiroKit.getUser();
        HttpContext.getRequest().getSession().setAttribute("shiroUser", shiroUser);
        //LogManager.me().executeLog(LogTaskFactory.loginLog(shiroUser.getId(), getIp()));
        return Response.success(shiroUser);
    }

    /**
     * 退出登录
     */
    @GetMapping(value = "/logout")
    public Response logOut() {
        //LogManager.me().executeLog(LogTaskFactory.exitLog(ShiroKit.getUser().getId(), getIp()));
        ShiroKit.getSubject().logout();
        // 删除所有cookie
        Cookie[] cookies = HttpContext.getRequest().getCookies();
        for (Cookie cookie : cookies) {
            Cookie temp = new Cookie(cookie.getName(), "");
            temp.setMaxAge(0);
            HttpContext.getResponse().addCookie(temp);
        }
        return Response.success("退出成功!");
    }
}