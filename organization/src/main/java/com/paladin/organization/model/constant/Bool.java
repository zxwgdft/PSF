package com.paladin.organization.model.constant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.paladin.framework.common.CodeEnum;

/**
 * @author TontoZhou
 * @since 2020/1/2
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Bool implements CodeEnum {
    YES(1, "是"),
    NO(0, "否");

    private int code;
    private String name;

    Bool(int code, String name) {
        this.code = code;
        this.name = name;
    }

    @JsonCreator
    public static Bool getEnum(Integer code) {
        if (code != null) {
            if (code == 1) return YES;
            else if (code == 0) return NO;
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
