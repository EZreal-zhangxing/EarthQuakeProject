package com.zx.Pojo;

public enum  MessageCode {
    MSG_SUCCESS("success", "成功！"),
    MSG_FAIL("fail", "失败！"),
    MSG_SUCCESS_CODE("success","验证码发送成功！"),
    MSG_FAIL_CODE("fail","验证码发送失败！"),
    MSG_CODE_ERROR("fail","验证码错误！"),
    MSG_FORMAT("formatException","转换异常！");

    private String code;

    private String description;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    MessageCode(String code, String description) {
        this.code = code;
        this.description = description;
    }
}