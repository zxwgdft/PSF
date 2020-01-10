package com.paladin.organization.service;

import com.paladin.organization.configuration.MongoCollection;
import com.paladin.organization.model.AppResourceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author TontoZhou
 * @since 2020/1/8
 */
@Service
public class AppResourceService {

    @Autowired
    private MongoTemplate mongoTemplate;


    public void createResourceModel(AppResourceModel model) {
        mongoTemplate.save(model, MongoCollection.APP_RESOURCE_MODEL.name());
    }


    public List<AppResourceModel> findAppResourceModels(String appId) {
        return mongoTemplate.find(new Query(Criteria.where(AppResourceModel.FIELD_APP_ID).is(appId)), AppResourceModel.class, MongoCollection.APP_RESOURCE_MODEL.name());
    }

}
