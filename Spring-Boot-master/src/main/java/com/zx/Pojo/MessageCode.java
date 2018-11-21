package com.zx.Pojo;

public enum  MessageCode {
    MSG_SUCCESS("success", "成功！"),
    MSG_FAIL("fail", "失败！"),
    MSG_SUCCESS_CODE("success","验证码发送成功！"),
    MSG_FAIL_CODE("fail","验证码发送失败！"),
    MSG_CODE_ERROR("fail","验证码错误！"),
    MSG_FORMAT("formatException","转换异常！"),

    MSG_ADMIN_LOGIN_FAIL("fail","登陆失败！"),
    MSG_ADMIN_LOGIN_SUCCESS("success","登陆成功！"),

    MSG_SCORE_SUCCESS("success","已经购买！"),
    MSG_SCORE_SUCCESS_DOWNLOAD("success","可以下载！"),

    MSG_USERINFO_SUCCESS("exist","用户已经提交过信息"),
    MSG_USERINFO_FAIL("notexist","用户未提交过"),

    MSG_COL_SUCCESS("success","取消收藏成功！"),
    MSG_SCORE_FAIL("fail","分数不足！");

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
