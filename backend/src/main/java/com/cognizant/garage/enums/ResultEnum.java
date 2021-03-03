package com.cognizant.garage.enums;

import lombok.Getter;

@Getter
public enum ResultEnum  {


    ORDER_NOT_FOUND(60, "OrderMain is not exit!"),
    
    VALID_ERROR(50, "Wrong information"),
    ;

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
