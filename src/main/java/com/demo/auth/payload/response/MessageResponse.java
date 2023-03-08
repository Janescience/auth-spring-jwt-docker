package com.demo.auth.payload.response;

import lombok.Data;

@Data
public class MessageResponse {

    private String message;
    private String code;
    private Object data;
    private String status;

    public MessageResponse(String message,String code,String status,Object data) {
        this.message = message;
        this.data = data;
        this.code = code;
        this.status = status;
    }
}
