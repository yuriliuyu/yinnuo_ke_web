package com.yuri.ynweb_kj.utils;

public enum EnumIsAnswer {

    YES(1,"是"),

    NO(-1, "否");

    private EnumIsAnswer(int code, String description) {
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
