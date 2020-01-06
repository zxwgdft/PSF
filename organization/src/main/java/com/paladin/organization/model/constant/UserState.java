package com.paladin.organization.model.constant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.paladin.framework.common.CodeEnum;

/**
 * @author TontoZhou
 * @since 2020/1/3
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum UserState implements CodeEnum {
    ENABLED(1, "可用"),
    STOP(0, "停用");

    UserState(int code, String name) {
        this.code = code;
        this.name = name;
    }

    @JsonCreator
    public static UserState getEnum(Integer code) {
        if (code != null) {
            for (UserState item : values()) {
                if (item.getCode() == code) {
                    return item;
                }
            }
        }
        return null;
    }

    private int code;
    private String name;

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getName() {
        return name;
    }
}
