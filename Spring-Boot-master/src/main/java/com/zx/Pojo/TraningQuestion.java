package com.zx.Pojo;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhangxing
 * @Date 2018/10/10 16:34
 */
public class TraningQuestion implements Serializable {
    private String id;
    private Integer traningId;
    private String title;
    private Integer type; // 1 选择 2 判断
    private Integer seqNo; //序号
    private String answer; //判断题 时为T/F 选择题时为选项

    private String createDate;

    private List<TraningAnswer> answers;

    public List<TraningAnswer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<TraningAnswer> answers) {
        this.answers = answers;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public TraningQuestion() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getTraningId() {
        return traningId;
    }

    public void setTraningId(Integer traningId) {
        this.traningId = traningId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(Integer seqNo) {
        this.seqNo = seqNo;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
