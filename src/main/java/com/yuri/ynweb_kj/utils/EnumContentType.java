package com.yuri.ynweb_kj.utils;

public enum EnumContentType {

    VIDEO(1,"视频"),

    TEXT(2, "图文"),

    TEST(3, "随堂测试"),

    CASE(4, "案例分析");

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
