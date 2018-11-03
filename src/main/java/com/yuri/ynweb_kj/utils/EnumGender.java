package com.yuri.ynweb_kj.utils;

public enum EnumGender {

    MALE(1,"男"),

    FEMALE(2, "女");

    private EnumGender(int code, String description) {
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
