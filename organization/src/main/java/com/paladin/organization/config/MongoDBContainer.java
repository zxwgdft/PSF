package com.paladin.organization.config;

import com.paladin.framework.spring.SpringContainer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

/**
 * @author TontoZhou
 * @since 2020/1/10
 */
@Component
@Slf4j
public class MongoDBContainer implements SpringContainer {

    @Autowired
    private MongoTemplate mongoTemplate;

    public boolean initialize() {

        for (MongoCollection coll : MongoCollection.values()) {
            String collName = coll.name();
            if (!mongoTemplate.collectionExists(collName)) {
                mongoTemplate.createCollection(collName);
                log.info("创建MongoDB集合[" + collName + "]");
            }
        }

        return true;
    }


}
