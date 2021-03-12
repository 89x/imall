package com.barry.enums;

/**
 *  是否枚举
 */
public enum  YesOrNo {
    NO(0, "否"),
    YES(1, "是");

    public final Integer Type;
    public final String  name;

    YesOrNo(Integer type, String name) {
        this.Type = type;
        this.name = name;
    }
}
