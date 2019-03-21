package com.byavs.frame.controller;

import com.byavs.frame.core.base.rest.Response;
import com.byavs.frame.service.UserAdminFunPermService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author XuYang
 * @description: 用户功能权限控制器
 * @date 2019/3/218:58
 */
@Api(description = "用户功能权限控制器")
@RestController
public class UserAdminFunPermController {

    @Autowired
    private UserAdminFunPermService userAdminFunPermService;


    @GetMapping("/list")
    @ApiOperation("列表2")
    public Response list2() {
        return Response.success(userAdminFunPermService.list2());
    }
}
