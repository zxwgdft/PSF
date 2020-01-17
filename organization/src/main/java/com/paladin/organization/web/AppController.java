package com.paladin.organization.web;

import com.paladin.framework.exception.BusinessException;
import com.paladin.organization.model.App;
import com.paladin.organization.service.AppService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author TontoZhou
 * @since 2019/11/1
 */
@Api("人员信息操作")
@RestController
@RequestMapping("/organization/app")
public class AppController {

    @Autowired
    private AppService appService;

    @ApiOperation(value = "获取某个应用")
    @GetMapping("/get")
    public App getApp(@RequestParam() String appId) {
        return appService.get(appId);
    }


    @ApiOperation(value = "获取所有应用")
    @GetMapping("/find/all")
    public List<App> findApps() {
        return appService.findAll();
    }


    @ApiOperation(value = "重定向跳转应用系统")
    @GetMapping("/redirect")
    public String redirectApp(String appId) {
        return appService.getRedirectAppUrl(appId);
    }
}
