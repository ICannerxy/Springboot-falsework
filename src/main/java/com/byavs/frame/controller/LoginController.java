package com.byavs.frame.controller;

import com.byavs.frame.core.entity.Response;
import com.byavs.frame.core.shiro.ShiroKit;
import com.byavs.frame.core.shiro.ShiroUser;
import com.byavs.frame.core.utli.HttpContext;
import com.byavs.frame.dao.model.SysUser;
import com.byavs.frame.domain.vo.SysUserVo;
import com.byavs.frame.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import java.util.List;

/**
 * @author XuYang
 * @description: TODO
 * @date 2019/4/313:31
 */
@Api(description = "登录模块")
@RestController
public class LoginController  {

    //@Autowired
    //private MenuService menuService;

    @Autowired
    private IUserService userService;

    /**
     * 跳转到主页
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public List<SysUser> list(@RequestBody SysUserVo sysUserVo) {
        System.out.println(sysUserVo.getAccount());
        return userService.listUser();
    }

    /**
     * 当没登录直接访问其他路由时,shiro的机制会自动进到这里
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public Response login() {
        return Response.failure("请先登录!");
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