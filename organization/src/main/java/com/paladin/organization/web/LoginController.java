package com.paladin.organization.web;

import com.paladin.framework.common.R;
import com.paladin.organization.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api("用户认证模块")
@Controller
@RequestMapping("/organization")
public class LoginController {

    @Autowired
    private SysUserService sysUserService;

    @ApiImplicitParams({@ApiImplicitParam(name = "newPassword", value = "新密码", required = true), @ApiImplicitParam(name = "oldPassword", value = "旧密码")})
    @RequestMapping(value = "/update/password", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Object updatePassword(@RequestParam String newPassword, @RequestParam String oldPassword) {
        return R.success();
    }


    @ApiOperation(value = "用户认证")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Object ajaxLogin(HttpServletRequest request, HttpServletResponse response, Model model) {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            return R.success();
        } else {
            return R.fail("sfsdfsd");
        }
    }

}
