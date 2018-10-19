package com.zx.Pojo;

import java.util.List;

public class VolunteerTeam {
    private Integer id;
    private String title;
    private String outUrl;
    private String url;
    private String description;
    private Integer teamNum;
    private String content;
    private Integer areaId;
    private Integer serviceType;
    private Integer teamManNum; //人数属性
    private Integer teamType;//
    private String createDate;
    private String teamMan;//团队联系人
    private String teamManTel;
    private String teamManEmail;
    private String teamAddress;
    private Integer signNum;

    private String areaName;
    private String teamTypeName;
    private String serviceName;

    public VolunteerTeam() {
    }

    public Integer getSignNum() {
        return signNum;
    }

    public void setSignNum(Integer signNum) {
        this.signNum = signNum;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public Integer getTeamManNum() {
        return teamManNum;
    }

    public void setTeamManNum(Integer teamManNum) {
        this.teamManNum = teamManNum;
    }


    public Integer getTeamType() {
        return teamType;
    }

    public void setTeamType(Integer teamType) {
        this.teamType = teamType;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getTeamNum() {
        return teamNum;
    }

    public void setTeamNum(Integer teamNum) {
        if(teamNum>=1 && teamNum<= 100){
            this.teamManNum=1;
        }else if(teamNum>=101 && teamNum<=200){
            this.teamManNum=2;
        }else if(teamNum>=201 && teamNum<=500){
            this.teamManNum=3;
        }else if(teamNum>=501 && teamNum<=1000){
            this.teamManNum=4;
        }else{
            this.teamManNum=5;
        }
        this.teamNum = teamNum;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getTeamMan() {
        return teamMan;
    }

    public void setTeamMan(String teamMan) {
        this.teamMan = teamMan;
    }

    public String getTeamManTel() {
        return teamManTel;
    }

    public void setTeamManTel(String teamManTel) {
        this.teamManTel = teamManTel;
    }

    public String getTeamManEmail() {
        return teamManEmail;
    }

    public void setTeamManEmail(String teamManEmail) {
        this.teamManEmail = teamManEmail;
    }

    public String getTeamAddress() {
        return teamAddress;
    }

    public void setTeamAddress(String teamAddress) {
        this.teamAddress = teamAddress;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getTeamTypeName() {
        return teamTypeName;
    }

    public void setTeamTypeName(String teamTypeName) {
        this.teamTypeName = teamTypeName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getOutUrl() {
        return outUrl;
    }

    public void setOutUrl(String outUrl) {
        this.outUrl = outUrl;
    }
}
