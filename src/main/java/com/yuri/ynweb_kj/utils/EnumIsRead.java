package com.yuri.ynweb_kj.utils;

public enum EnumIsRead {

    UNREAD(-1,"未读"),

    READED(1, "已读");

    private EnumIsRead(int code, String description) {
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
