package com.zx.Pojo;

import java.util.List;

public class VolunteerPolicy {
    private Integer id;
    private Integer modelId;
    private String title;
    private String description;
    private String content;
    private String outUrl;
    private String imageUrl;
    private Integer viewNum;
    private Integer commonNum;
    private String createDate;

    private String modelName;

    private List<Common> policyCommonList;

    public VolunteerPolicy() {

    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOutUrl() {
        return outUrl;
    }

    public void setOutUrl(String outUrl) {
        this.outUrl = outUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getViewNum() {
        return viewNum;
    }

    public void setViewNum(Integer viewNum) {
        this.viewNum = viewNum;
    }

    public Integer getCommonNum() {
        return commonNum;
    }

    public void setCommonNum(Integer commonNum) {
        this.commonNum = commonNum;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public List<Common> getPolicyCommonList() {
        return policyCommonList;
    }

    public void setPolicyCommonList(List<Common> policyCommonList) {
        this.policyCommonList = policyCommonList;
    }
}
