package com.yuri.ynweb_kj.utils;

public enum EnumCreditType {

    VIDEO(5,"视频"),

    SIGNIN(2, "签到"),

    TEST(3, "测试");

    private EnumCreditType(int code, String description) {
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
