package com.paladin.supervise.model.organization.constant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.paladin.framework.common.CodeEnum;

/**
 * @author TontoZhou
 * @since 2020/1/3
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Sex implements CodeEnum {
    MALE(1, "男"),
    FEMALE(2, "女");

    private int code;
    private String name;

    Sex(int code, String name) {
        this.code = code;
        this.name = name;
    }

    @JsonCreator
    public static Sex getEnum(Integer code) {
        if (code != null) {
            for (Sex item : values()) {
                if (item.getCode() == code) {
                    return item;
                }
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}