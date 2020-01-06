package com.paladin.organization.web.rest;

import com.paladin.framework.common.R;
import com.paladin.organization.model.constant.Sex;
import com.paladin.organization.service.SysUserService;
import com.paladin.organization.service.dto.LoginUser;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation(value = "账号认证")
    @PostMapping("/authenticate")
    public R<String> authenticateByAccount(@RequestBody LoginUser loginUser, @RequestParam("sex") Sex sex) {
        R<String> result = sysUserService.authenticate(loginUser);
        return result;
    }


}
