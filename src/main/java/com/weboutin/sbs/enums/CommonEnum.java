package com.weboutin.sbs.enums;

public enum CommonEnum {
    ARGUMENT_INVALID(-1, "参数异常"), LOGIN_SUCCESS(1001, "登录成功");

    private Integer code;

    private String msg;

    private CommonEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }
}