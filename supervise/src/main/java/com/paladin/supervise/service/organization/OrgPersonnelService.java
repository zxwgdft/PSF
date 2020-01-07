package com.paladin.supervise.service.organization;

import com.paladin.supervise.model.organization.OrgPersonnel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author TontoZhou
 * @since 2019/11/1
 */
@FeignClient(value = "service-organization")
public interface OrgPersonnelService {

    @GetMapping(value = "/organization/personnel/get")
    OrgPersonnel getPersonnel(@RequestParam("userId") String userId);
}
