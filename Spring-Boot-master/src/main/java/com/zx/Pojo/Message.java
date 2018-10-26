package com.zx.Pojo;

import java.io.Serializable;

public class Message implements Serializable {
    private String code;
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Message(MessageCode mc){
        this.code=mc.getCode();
        this.message=mc.getDescription();
    }
}
