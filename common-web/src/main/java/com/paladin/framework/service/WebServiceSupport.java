package com.paladin.framework.service;

import com.paladin.framework.common.BaseModel;

import java.util.Date;

/**
 * @author TontoZhou
 * @since 2019/12/25
 */
public class WebServiceSupport<Model> extends ServiceSupport<Model> {

    /**
     * 更新操作前需要对数据包裹，例如设置更新操作人与操作时间
     *
     * @param model
     */
    public void updateModelWrap(Model model) {
        if (isBaseModel) {
            Date now = new Date();
            UserSession userSession = UserSessionThreadManager.getCurrentUserSession();
            String uid = userSession == null ? "" : userSession.getUserId();
            BaseModel baseModel = (BaseModel) model;
            baseModel.setUpdateTime(now);
            baseModel.setUpdateBy(uid);
        }
    }

    /**
     * 保存操作前需要对数据包裹，例如设置创建操作人与操作时间
     *
     * @param model
     */
    public void saveModelWrap(Model model) {
        if (isBaseModel) {
            Date now = new Date();
            UserSession userSession = UserSessionThreadManager.getCurrentUserSession();
            String uid = userSession == null ? "" : userSession.getUserId();
            BaseModel baseModel = (BaseModel) model;
            baseModel.setCreateTime(now);
            baseModel.setCreateBy(uid);
            baseModel.setUpdateTime(now);
            baseModel.setUpdateBy(uid);
            baseModel.setDeleted(false);
        }
    }

}
