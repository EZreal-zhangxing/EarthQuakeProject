package com.zx.Pojo;

import java.io.Serializable;

/**
 * @author zhangxing
 * @Date 2018/10/15 17:05
 */
public class TraningAnswer implements Serializable {
    private Integer id;
    private String questionId;
    private String selectName;
    private String selectContent;
    private String createDate;

    public TraningAnswer() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getSelectName() {
        return selectName;
    }

    public void setSelectName(String selectName) {
        this.selectName = selectName;
    }

    public String getSelectContent() {
        return selectContent;
    }

    public void setSelectContent(String selectContent) {
        this.selectContent = selectContent;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
