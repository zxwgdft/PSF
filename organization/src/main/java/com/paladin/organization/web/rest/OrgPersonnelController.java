package com.paladin.organization.web.rest;

import com.paladin.framework.service.OffsetPage;
import com.paladin.framework.service.PageResult;
import com.paladin.organization.model.OrgPersonnel;
import com.paladin.organization.service.OrgPersonnelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author TontoZhou
 * @since 2019/11/1
 */
@RestController
@RequestMapping("/organization/personnel")
public class OrgPersonnelController {

    @Autowired
    private OrgPersonnelService orgPersonnelService;

    @GetMapping("/get")
    public OrgPersonnel getPersonnel(@RequestParam() String userId) {
        return orgPersonnelService.get(userId);
    }

    @GetMapping("/find")
    public PageResult<OrgPersonnel> findPersonnel(OffsetPage param) {
        return orgPersonnelService.searchPage(param);
    }


}
