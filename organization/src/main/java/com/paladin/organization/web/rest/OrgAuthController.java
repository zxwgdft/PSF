package com.paladin.organization.web.rest;

import com.paladin.framework.common.R;
import com.paladin.organization.service.SysUserService;
import com.paladin.organization.service.dto.LoginUser;
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
@RestController
@RequestMapping("/organization")
public class OrgAuthController {


    @Autowired
    private SysUserService sysUserService;


    @PostMapping("/authenticate")
    public ResponseEntity authenticateByAccount(@RequestBody LoginUser loginUser) {
        R result = sysUserService.authenticate(loginUser);
        return ResponseEntity.ok(result);
    }


}
