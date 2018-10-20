package com.zx.Pojo;

import java.io.Serializable;

/**
 * @author zhangxing
 * @Date 2018/10/20 16:02
 */
public class UserTraning implements Serializable {
    private Integer id;
    private Integer userId;

    private Integer traningId;
    private String traningtite;
    private String traningimgUrl;
    private Integer traningReadNum;
    private Integer collectionNum;


    private String havewatchTime;
    private Integer type; // 0 未看完 1已看完
    private String createDate;

    public Integer getTraningId() {
        return traningId;
    }

    public void setTraningId(Integer traningId) {
        this.traningId = traningId;
    }

    public String getTraningtite() {
        return traningtite;
    }

    public void setTraningtite(String traningtite) {
        this.traningtite = traningtite;
    }

    public String getTraningimgUrl() {
        return traningimgUrl;
    }

    public void setTraningimgUrl(String traningimgUrl) {
        this.traningimgUrl = traningimgUrl;
    }

    public Integer getTraningReadNum() {
        return traningReadNum;
    }

    public void setTraningReadNum(Integer traningReadNum) {
        this.traningReadNum = traningReadNum;
    }

    public Integer getCollectionNum() {
        return collectionNum;
    }

    public void setCollectionNum(Integer collectionNum) {
        this.collectionNum = collectionNum;
    }

    public UserTraning() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getHavewatchTime() {
        return havewatchTime;
    }

    public void setHavewatchTime(String havewatchTime) {
        this.havewatchTime = havewatchTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
