package com.paladin.organization.web.rest;

import com.paladin.framework.service.OffsetPage;
import com.paladin.framework.service.PageResult;
import com.paladin.organization.model.OrgPersonnel;
import com.paladin.organization.service.OrgPersonnelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiKeyAuthDefinition;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author TontoZhou
 * @since 2019/11/1
 */
@Api("人员信息操作")
@RestController
@RequestMapping("/organization/personnel")
public class OrgPersonnelController {

    @Autowired
    private OrgPersonnelService orgPersonnelService;


    @ApiKeyAuthDefinition(in = ApiKeyAuthDefinition.ApiKeyLocation.HEADER, name = "TOKEN", key = "auth")
    @ApiOperation(value = "获取某个人员信息")
    @GetMapping("/get")
    public OrgPersonnel getPersonnel(@RequestParam() String userId) {
        return orgPersonnelService.get(userId);
    }


    @ApiOperation(value = "获取人员信息列表-分页")
    //@ApiOperation(value = "获取人员信息列表-分页", response = OrgPersonnel.class, responseContainer = "List")
    @GetMapping("/find")
    public PageResult<OrgPersonnel> findPersonnel(OffsetPage param) {
        return orgPersonnelService.searchPage(param);
    }


}
