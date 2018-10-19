package com.zx.Pojo;

import java.io.Serializable;

/**
 * @author zhangxing
 * @Date 2018/10/18 14:25
 */
public class Examination implements Serializable {
    private String examId;
    private String title;
    private String createDate;

    public Examination() {
    }

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
