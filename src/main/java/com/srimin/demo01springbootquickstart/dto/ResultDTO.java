package com.srimin.demo01springbootquickstart.dto;

import com.srimin.demo01springbootquickstart.exception.CustomizeErrorCode;
import com.srimin.demo01springbootquickstart.exception.CustomizeException;

public class ResultDTO {
    private Integer code;
    private String message;

    public static ResultDTO errorOf (Integer code, String message){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }

    public static ResultDTO errorOf(CustomizeErrorCode errorCode) {
        return errorOf(errorCode.getCode(),errorCode.getMessage());
    }

    public static ResultDTO errorOf(CustomizeException e) {
        return errorOf(e.getCode(),e.getMessage());
    }
    public static ResultDTO okOf() {
        return errorOf(200,"请求成功");
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
