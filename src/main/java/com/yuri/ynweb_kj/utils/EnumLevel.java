package com.yuri.ynweb_kj.utils;

public enum EnumLevel {
    LEVEL1(1,"level1"),

    LEVEL2(2, "level2"),

    LEVEL3(3, "level3");


    private EnumLevel(int code, String description) {
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
