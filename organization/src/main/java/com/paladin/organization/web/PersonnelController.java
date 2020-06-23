package com.paladin.organization.web;

import com.paladin.framework.service.OffsetPage;
import com.paladin.framework.service.PageResult;
import com.paladin.organization.model.Personnel;
import com.paladin.organization.service.PersonnelService;
import io.swagger.annotations.Api;
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
@Api("人员管理")
@RestController
@RequestMapping("/organization/personnel")
public class PersonnelController {

    @Autowired
    private PersonnelService personnelService;

    @ApiOperation(value = "获取某个人员信息")
    @GetMapping("/get")
    public Personnel getPersonnel(@RequestParam() String userId) {
        return personnelService.get(userId);
    }


    @ApiOperation(value = "获取人员信息列表-分页")
    @GetMapping("/find")
    public PageResult<Personnel> findPersonnel(OffsetPage param) {
        return personnelService.searchPage(param);
    }


}
