package com.barry.enums;

/**
 *  性别枚举
 */
public enum  Sex {
    women(0,"女"),
    man(1,"男"),
    secret(2,"保密");

    public final Integer Type;
    public final String Value;

    Sex(Integer type, String value) {
        Type = type;
        Value = value;
    }
}
