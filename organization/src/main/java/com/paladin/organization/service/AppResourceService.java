package com.paladin.organization.service;

import com.paladin.framework.exception.BusinessException;
import com.paladin.organization.configuration.MongoCollection;
import com.paladin.organization.model.AppResource;
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


    public void createResource(AppResource resource) {
        String parentId = resource.getParent();
        if (parentId != null && parentId.length() > 0) {
            AppResource parent = getAppResource(parentId);
            if (parent == null) {
                throw new BusinessException("无法创建应用资源，因为找不到上级资源[ID:" + parentId + "]");
            }

            // 拼接路径
            String path = parent.getPath();
            if (path == null) {
                path = "";
            }
            path += parentId + ",";

            resource.setPath(path);
        }
        mongoTemplate.save(resource, MongoCollection.APP_RESOURCE.name());
    }

    public AppResource getAppResource(String id) {
        return mongoTemplate.findById(id, AppResource.class, MongoCollection.APP_RESOURCE.name());
    }

    public List<AppResource> findAppResource(String appId) {
        return mongoTemplate.find(new Query(Criteria.where(AppResource.FIELD_APP_ID).is(appId)), AppResource.class, MongoCollection.APP_RESOURCE.name());
    }

    public void createResourceModel(AppResourceModel model) {
        mongoTemplate.save(model, MongoCollection.APP_RESOURCE_MODEL.name());
    }

    public List<AppResourceModel> findAppResourceModels(String appId) {
        return mongoTemplate.find(new Query(Criteria.where(AppResourceModel.FIELD_APP_ID).is(appId)), AppResourceModel.class, MongoCollection.APP_RESOURCE_MODEL.name());
    }

    public AppResourceModel getAppResourceModel(String id) {
        return mongoTemplate.findById(id, AppResourceModel.class, MongoCollection.APP_RESOURCE_MODEL.name());
    }


}
