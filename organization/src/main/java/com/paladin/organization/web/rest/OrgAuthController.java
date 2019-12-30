package com.paladin.organization.web.rest;

import com.paladin.framework.common.R;
import com.paladin.organization.service.SysUserService;
import com.paladin.organization.service.dto.LoginUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
public class OrgAuthController {

    @Autowired
    private SysUserService sysUserService;

    @ApiOperation(value = "账号认证", response = R.class)
    @PostMapping("/authenticate")
    public ResponseEntity authenticateByAccount(@RequestBody LoginUser loginUser) {
        R result = sysUserService.authenticate(loginUser);
        return ResponseEntity.ok(result);
    }


}
