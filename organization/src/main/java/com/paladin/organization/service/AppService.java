package com.paladin.organization.service;

import com.paladin.framework.service.Condition;
import com.paladin.framework.service.QueryType;
import com.paladin.framework.service.WebServiceSupport;
import com.paladin.organization.model.App;
import org.springframework.stereotype.Service;

/**
 * @author TontoZhou
 * @since 2019/12/12
 */
@Service
public class AppService extends WebServiceSupport<App> {

    public App getAppByClient(String clientId) {
        return searchOne(new Condition(App.FIELD_CLIENT_ID, QueryType.EQUAL, clientId));
    }
}
