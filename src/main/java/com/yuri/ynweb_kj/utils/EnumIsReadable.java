package com.yuri.ynweb_kj.utils;

public enum EnumIsReadable {

    READABLE(1,"可读"),

    UNREADABLE(-1, "不可读");

    private EnumIsReadable(int code, String description) {
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
