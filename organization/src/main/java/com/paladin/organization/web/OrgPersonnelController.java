package com.paladin.organization.web;

import com.paladin.organization.model.OrgPersonnel;
import com.paladin.organization.service.OrgPersonnelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author TontoZhou
 * @since 2019/11/1
 */
@RestController
@RequestMapping("/organization/personnel")
public class OrgPersonnelController {

    @Autowired
    private OrgPersonnelService orgPersonnelService;

    @GetMapping("/find")
    public List<OrgPersonnel> findPersonnel() {
        return orgPersonnelService.findPage(0, 10).getData();
    }

}
