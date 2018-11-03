package com.yuri.ynweb_kj.utils;

public enum EnumUserType {
    /**
     * 学生
     */
    STUDENT(1),
    /**
     * 老师
     */
    TEACHER(2);

    private EnumUserType(int status) {
        this.status = status;
    }

    private int status;

    public int value() {
        return status;
    }
}
