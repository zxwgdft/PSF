package com.paladin.supervise.service;

import com.paladin.supervise.service.dto.OrgPersonnel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author TontoZhou
 * @since 2019/11/1
 */
@FeignClient(value = "service-organization")
public interface OrgPersonnelService {

    @GetMapping(value = "/organization/personnel/find")
    List<OrgPersonnel> findPersonnel();
}
