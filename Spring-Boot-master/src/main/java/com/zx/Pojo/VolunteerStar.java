package com.zx.Pojo;

import java.util.List;

/**
 * 志愿之星 年度人物评选
 * 志愿者风采
 * POJO类
 */
public class VolunteerStar {
    private Integer id;
    private Integer modelId;
    private String title;
    private String outUrl; //文章外链
    private String imageUrl; //文章展示图片
    private String description;
    private String content;
    private Integer areaId;
    private String createDate;

    private Integer commonNum;
    private Integer viewNum;
    private Integer articalNum;

    private String modelName;
    private String areaName;

    private List<Common> commons;

    public VolunteerStar() {
    }

    public List<Common> getCommons() {
        return commons;
    }

    public void setCommons(List<Common> commons) {
        this.commons = commons;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOutUrl() {
        return outUrl;
    }

    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
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

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Integer getCommonNum() {
        return commonNum;
    }

    public void setCommonNum(Integer commonNum) {
        this.commonNum = commonNum;
    }

    public Integer getViewNum() {
        return viewNum;
    }

    public void setViewNum(Integer viewNum) {
        this.viewNum = viewNum;
    }

    public Integer getArticalNum() {
        return articalNum;
    }

    public void setArticalNum(Integer articalNum) {
        this.articalNum = articalNum;
    }
}
