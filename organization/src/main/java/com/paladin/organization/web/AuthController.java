package com.paladin.organization.web;

import com.paladin.organization.core.AuthenticationManager;
import com.paladin.organization.service.dto.LoginApp;
import com.paladin.organization.service.dto.LoginUser;
import com.paladin.organization.service.dto.OAuthParam;
import com.paladin.organization.service.vo.OpenToken;
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

    //TODO 是否固定域名跨域，还是对所有（安全性）
    @ApiOperation(value = "用户账号认证")
    @PostMapping("/authenticate/user")
    public OpenToken authenticateByAccount(@RequestBody LoginUser loginUser) {
        return authenticationManager.authenticateAndCreateToken(loginUser);
    }

    //TODO 应用客户端后台认证，这里对所有跨域，但是会
    @ApiOperation(value = "应用客户端认证")
    @PostMapping("/authenticate/app")
    public OpenToken authenticateByApp(@RequestBody LoginApp loginApp) {
        return authenticationManager.authenticateAndCreateToken(loginApp);
    }

    //TODO 如果登录h5页面由我们服务提供，则需要限定域名提高安全性，否则失去跳转我们页面进行页面的认证的意义
    @ApiOperation(value = "oauth2第三方授权认证")
    @PostMapping("/authenticate/oauth")
    public String authenticateByApp(@RequestBody OAuthParam oauthParam) {
        return authenticationManager.authenticateAndRedirect(oauthParam);
    }


}
