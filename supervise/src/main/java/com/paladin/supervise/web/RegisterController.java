package com.paladin.supervise.web;

import com.paladin.supervise.service.OrgPersonnelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author TontoZhou
 * @since 2019/11/1
 */
@RestController
@RequestMapping("/supervise/register")
public class RegisterController {

    @Autowired
    private OrgPersonnelService orgPersonService;

    @GetMapping("/do")
    public Object findPerson() {
        return orgPersonService.findPersonnel();
    }

}
