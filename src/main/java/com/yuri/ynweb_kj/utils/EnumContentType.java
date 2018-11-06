package com.yuri.ynweb_kj.utils;

public enum EnumContentType {

    VIDEO(1,"男"),

    TEXT(2, "女"),

    TEST(3, "测试"),

    CASE(4, "案例");

    private EnumContentType(int code, String description) {
        this.code = code;
        this.description = description;
    }

    private int code;
    private String description;

    public int value() {
        return code;
    }
    public String getDescription(){
        return description;
    }
}
