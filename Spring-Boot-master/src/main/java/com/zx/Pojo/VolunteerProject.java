package com.zx.Pojo;

import java.util.List;

public class VolunteerProject {
    private Integer id;
    private String url;
    private String showImage;
    private String title;
    private String description;
    private String content;
    private Integer areaId;
    private String areaName;
    private Integer serviceType;
    private String serviceTypeName;
    private Integer projectStatue;
    private String projectStatueName;
    private Integer signArea;
    private String signAreaName;
    private Integer serviceTo;
    private String serviceToName;
    private Integer projectManNum;

    private String createDate;
    private String endDate;
    private Integer signNum;

    private String createMan; //项目发起人
    private String projectMan; //项目联系人
    private String projectManTel; //项目联系人电话
    private String projectManEmail; //联系人邮箱
    private String projectAddress; //项目地址

    //岗位列表
    private List<Station> stations;

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

    public Integer getServiceType() {
        return serviceType;
    }

    public void setServiceType(Integer serviceType) {
        this.serviceType = serviceType;
    }

    public String getServiceTypeName() {
        return serviceTypeName;
    }

    public void setServiceTypeName(String serviceTypeName) {
        this.serviceTypeName = serviceTypeName;
    }

    public Integer getProjectStatue() {
        return projectStatue;
    }

    public void setProjectStatue(Integer projectStatue) {
        this.projectStatue = projectStatue;
    }

    public String getProjectStatueName() {
        return projectStatueName;
    }

    public void setProjectStatueName(String projectStatueName) {
        this.projectStatueName = projectStatueName;
    }

    public Integer getSignArea() {
        return signArea;
    }

    public void setSignArea(Integer signArea) {
        this.signArea = signArea;
    }

    public String getSignAreaName() {
        return signAreaName;
    }

    public void setSignAreaName(String signAreaName) {
        this.signAreaName = signAreaName;
    }

    public Integer getServiceTo() {
        return serviceTo;
    }

    public void setServiceTo(Integer serviceTo) {
        this.serviceTo = serviceTo;
    }

    public String getServiceToName() {
        return serviceToName;
    }

    public void setServiceToName(String serviceToName) {
        this.serviceToName = serviceToName;
    }

    public Integer getProjectManNum() {
        return projectManNum;
    }

    public void setProjectManNum(Integer projectManNum) {
        this.projectManNum = projectManNum;
    }

    public String getShowImage() {
        return showImage;
    }

    public void setShowImage(String showImage) {
        this.showImage = showImage;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getSignNum() {
        return signNum;
    }

    public void setSignNum(Integer signNum) {
        this.signNum = signNum;
    }

    public String getUrl() {
        return url;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCreateMan() {
        return createMan;
    }

    public void setCreateMan(String createMan) {
        this.createMan = createMan;
    }

    public String getProjectMan() {
        return projectMan;
    }

    public void setProjectMan(String projectMan) {
        this.projectMan = projectMan;
    }

    public String getProjectManTel() {
        return projectManTel;
    }

    public void setProjectManTel(String projectManTel) {
        this.projectManTel = projectManTel;
    }

    public String getProjectManEmail() {
        return projectManEmail;
    }

    public void setProjectManEmail(String projectManEmail) {
        this.projectManEmail = projectManEmail;
    }

    public String getProjectAddress() {
        return projectAddress;
    }

    public void setProjectAddress(String projectAddress) {
        this.projectAddress = projectAddress;
    }

    public List<Station> getStations() {
        return stations;
    }

    public void setStations(List<Station> stations) {
        this.stations = stations;
    }

    public VolunteerProject() {
    }
}
