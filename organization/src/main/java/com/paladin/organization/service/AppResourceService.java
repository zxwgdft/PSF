package com.paladin.organization.service;

import com.mongodb.client.result.UpdateResult;
import com.paladin.framework.exception.BusinessException;
import com.paladin.framework.utils.StringUtil;
import com.paladin.framework.utils.convert.SimpleBeanCopyUtil;
import com.paladin.organization.model.App;
import com.paladin.organization.model.AppResource;
import com.paladin.organization.model.AppResourceModel;
import com.paladin.organization.model.DynamicProperty;
import com.paladin.organization.service.constant.MongoCollection;
import com.paladin.organization.service.dto.AppResourceModelSave;
import com.paladin.organization.service.dto.AppResourceSave;
import com.paladin.organization.service.dto.AppResourceUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author TontoZhou
 * @since 2020/1/8
 */
@Service
public class AppResourceService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private AppService appService;

    /**
     * 创建资源
     *
     * @param resourceSave
     */
    public AppResource createResource(AppResourceSave resourceSave) {
        AppResource appResource = SimpleBeanCopyUtil.simpleCopy(resourceSave, new AppResource());

        String appId = appResource.getAppId();
        String modelId = appResource.getModelId();

        App app = appService.get(appId);
        if (app == null) {
            throw new BusinessException("找不到资源对应应用");
        }

        Map<String, Object> effectiveProperties = checkEffectiveProperties(resourceSave.getProperties(), modelId);
        appResource.setProperties(effectiveProperties);

        String parentId = appResource.getParent();
        if (StringUtil.isNotEmpty(parentId)) {
            AppResource parent = mongoTemplate.findById(parentId, AppResource.class, MongoCollection.APP_RESOURCE);
            if (parent == null) {
                throw new BusinessException("无法创建应用资源，因为找不到上级资源[ID:" + parentId + "]");
            }
        }

        return mongoTemplate.save(appResource, MongoCollection.APP_RESOURCE);
    }

    /**
     * 检查并获取有效的属性
     *
     * @param properties
     * @param modelId
     * @return
     */
    public Map<String, Object> checkEffectiveProperties(Map<String, Object> properties, String modelId) {
        AppResourceModel model = mongoTemplate.findById(modelId, AppResourceModel.class, MongoCollection.APP_RESOURCE_MODEL);
        if (model == null) {
            throw new BusinessException("资源模型不存在");
        }
        Map<String, Object> effectProperties = new HashMap<>();
        List<DynamicProperty> dynamicProperties = model.getProperties();
        for (DynamicProperty dynamicProperty : dynamicProperties) {
            String code = dynamicProperty.getCode();
            Object value = properties == null ? null : properties.get(code);
            if (value == null || "".equals(value)) {
                String defaultValue = dynamicProperty.getDefaultValue();
                if (defaultValue == null || defaultValue.length() == 0) {
                    if (!dynamicProperty.isNullable()) {
                        throw new BusinessException("属性[" + code + "]的值不能为空");
                    }
                } else {
                    effectProperties.put(code, defaultValue);
                }
            } else {
                effectProperties.put(code, value);
            }
        }
        return effectProperties;
    }


    /**
     * 更新资源属性
     *
     * @param resourceUpdate
     * @return
     */
    public boolean updateResourceProperties(AppResourceUpdate resourceUpdate) {
        String resourceId = resourceUpdate.getId();
        Map<String, Object> properties = checkEffectiveProperties(resourceUpdate.getProperties(), resourceUpdate.getModelId());
        Query query = new Query(Criteria.where(AppResource.FIELD_ID).is(resourceId));
        Update update = new Update()
                .set(AppResource.FIELD_PROPERTIES, properties)
                .set(AppResource.FIELD_MODEL_ID, resourceUpdate.getModelId());
        UpdateResult updateResult = mongoTemplate.updateMulti(query, update, MongoCollection.APP_RESOURCE);
        System.out.println(updateResult.getMatchedCount() + "/" + updateResult.getModifiedCount());
        return true;
    }

    /**
     * 更新资源父节点
     *
     * @param resourceId
     * @param parentId
     */
    public boolean updateResourceParent(String resourceId, String parentId) {
        Query query = new Query(Criteria.where(AppResource.FIELD_ID).is(resourceId));
        Update update = Update.update(AppResource.FIELD_PARENT, parentId);
        UpdateResult updateResult = mongoTemplate.updateMulti(query, update, MongoCollection.APP_RESOURCE);
        System.out.println(updateResult.getMatchedCount() + "/" + updateResult.getModifiedCount());
        return true;
    }


    /**
     * 查找APP下所有资源
     *
     * @param appId
     * @return
     */
    public List<AppResource> findAppResource(String appId) {
        return mongoTemplate.find(new Query(Criteria.where(AppResource.FIELD_APP_ID).is(appId)), AppResource.class, MongoCollection.APP_RESOURCE);
    }

    /**
     * 创建资源模型
     *
     * @param modelSave
     * @return
     */
    public AppResourceModel createResourceModel(AppResourceModelSave modelSave) {
        AppResourceModel resourceModel = SimpleBeanCopyUtil.simpleCopy(modelSave, new AppResourceModel());
        return mongoTemplate.save(resourceModel, MongoCollection.APP_RESOURCE_MODEL);
    }


    public List<AppResourceModel> findAppResourceModels(String appId) {
        return mongoTemplate.find(new Query(Criteria.where(AppResourceModel.FIELD_APP_ID).is(appId)), AppResourceModel.class, MongoCollection.APP_RESOURCE_MODEL);
    }


}
