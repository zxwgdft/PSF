package com.paladin.supervise.auth;

import com.paladin.framework.exception.BusinessException;
import com.paladin.framework.service.UserSession;
import com.paladin.framework.service.UserSessionFactory;
import com.paladin.supervise.model.organization.OrgPersonnel;
import com.paladin.supervise.service.organization.OrgPersonnelService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author TontoZhou
 * @since 2020/1/2
 */

public class SuperviseUserSessionFactory implements UserSessionFactory {

    @Autowired
    private OrgPersonnelService orgPersonnelService;

    @Override
    public UserSession createUserSession(String subject) {
        OrgPersonnel personnel = orgPersonnelService.getPersonnel(subject);
        if (personnel == null) {
            throw new BusinessException("从平台同步用户信息失败");
        }
        return new SuperviseUserSession(personnel);
    }

}
