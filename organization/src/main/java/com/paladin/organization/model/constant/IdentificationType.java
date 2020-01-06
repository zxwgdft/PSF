package com.paladin.organization.model.constant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.paladin.framework.common.CodeEnum;

/**
 * @author TontoZhou
 * @since 2020/1/3
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum IdentificationType implements CodeEnum {
    SFZ(1, "身份证"),
    JMHKB(2, "居民户口簿"),
    HZ(2, "护照"),
    JGZ(2, "军官证"),
    JSZ(2, "驾驶执照"),
    GATXZ(2, "港澳居民来往内地通行证"),
    TWTXZ(2, "台湾居民来往内地通行证"),
    QT(2, "其他");

    private int code;
    private String name;

    IdentificationType(int code, String name) {
        this.code = code;
        this.name = name;
    }

    @JsonCreator
    public static IdentificationType getEnum(Integer code) {
        if (code != null) {
            for (IdentificationType item : values()) {
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