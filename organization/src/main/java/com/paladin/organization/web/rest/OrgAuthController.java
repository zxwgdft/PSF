package com.paladin.organization.web.rest;

import com.paladin.framework.common.HttpCode;
import com.paladin.framework.common.R;
import com.paladin.organization.model.SysUser;
import com.paladin.organization.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author TontoZhou
 * @since 2019/12/23
 */
@RestController
@RequestMapping("/organization/auth")
public class OrgAuthController {

    @Autowired
    private SysUserService sysUserService;

    @PostMapping("/authenticate")
    public R authenticateByAccount(@RequestParam("username") String username, @RequestParam("password") String password) {
        SysUser sysUser = sysUserService.getUserByAccount(username);
        if (sysUser != null) {
            return R.success("1111111");
        }

        return R.fail(HttpCode.UNAUTHORIZED);
    }


}
