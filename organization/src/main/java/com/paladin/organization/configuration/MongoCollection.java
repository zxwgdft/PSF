package com.paladin.organization.configuration;

/**
 * @author TontoZhou
 * @since 2020/1/10
 */
public enum MongoCollection {

    APP_RESOURCE("应用资源"),
    APP_RESOURCE_MODEL("应用资源实体类集合");


    String comment;

    MongoCollection(String comment) {
        this.comment = comment;
    }
}
