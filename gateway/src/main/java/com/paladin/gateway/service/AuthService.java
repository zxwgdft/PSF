package com.paladin.gateway.service;

import com.paladin.framework.common.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author TontoZhou
 * @since 2019/12/23
 */
@FeignClient(value = "service-organization")
public interface AuthService {

    @PostMapping(value = "/organization/auth/authenticate")
    R authenticate(@RequestParam("username")String username, @RequestParam("password")String password);

}
