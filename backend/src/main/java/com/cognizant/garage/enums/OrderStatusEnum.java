package com.cognizant.garage.enums;


public enum OrderStatusEnum implements CodeEnum {
    NEW(0, "New OrderMain")
    ;

    private  int code;
    private String msg;

    OrderStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
