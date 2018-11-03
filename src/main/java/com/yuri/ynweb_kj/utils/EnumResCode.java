package com.yuri.ynweb_kj.utils;

public enum EnumResCode {
    /**
     * server成功
     */
    SUCCESSFUL(0),
    /**
     * server失败
     */
    SERVER_ERROR(-1);

    private EnumResCode(int status) {
        this.status = status;
    }

    private int status;

    public int value() {
        return status;
    }
}
