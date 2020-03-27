package com.paladin.organization.web;

import com.paladin.framework.common.R;
import com.paladin.organization.core.AuthenticationManager;
import com.paladin.organization.service.dto.LoginApp;
import com.paladin.organization.service.dto.LoginUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author TontoZhou
 * @since 2019/12/23
 */
@Api("人员认证")
@RestController
@RequestMapping("/organization")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @ApiOperation(value = "用户账号认证")
    @PostMapping("/authenticate/user")
    public R authenticateByAccount(@RequestBody LoginUser loginUser) {
        return authenticationManager.authenticate(loginUser);
    }

    @ApiOperation(value = "应用客户端认证")
    @PostMapping("/authenticate/app")
    public R authenticateByApp(@RequestBody LoginApp loginApp) {
        return authenticationManager.authenticate(loginApp);
    }
}
