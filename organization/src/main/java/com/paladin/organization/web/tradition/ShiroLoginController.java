package com.paladin.organization.web.tradition;

import com.paladin.framework.common.R;
import com.paladin.organization.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Api("用户认证模块")
@Controller
@ConditionalOnProperty(prefix = "paladin", value = "shiro-enabled", havingValue = "true", matchIfMissing = false)
@RequestMapping("/organization")
public class ShiroLoginController {

    @Autowired
    private SysUserService sysUserService;

    @ApiOperation(value = "登录成功首页")
    @GetMapping("/index")
    public String index() {
        return "/organization/index";
    }

    @ApiOperation(value = "登录页面")
    @GetMapping("/login")
    public String loginInput() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            return index();
        }
        return "/organization/login";
    }

    @ApiOperation(value = "用户认证")
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.TEXT_HTML_VALUE)
    public String login() {
        // 能进到该方法一定已经登录成功，直接返回
        return index();
    }

    @ApiOperation(value = "用户认证")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public R ajaxLogin() {
        // 能进到该方法一定已经登录成功，直接返回
        Subject subject = SecurityUtils.getSubject();
        return R.success(subject.getSession().getId());
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "newPassword", value = "新密码", required = true), @ApiImplicitParam(name = "oldPassword", value = "旧密码")})
    @RequestMapping(value = "/update/password", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public R updatePassword(@RequestParam String newPassword, @RequestParam String oldPassword) {
        return R.success();
    }
}
