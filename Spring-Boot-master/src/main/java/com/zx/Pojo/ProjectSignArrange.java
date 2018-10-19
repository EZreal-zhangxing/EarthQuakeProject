package com.zx.Pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zhangxing
 * @Date 2018/10/9 11:16
 */
public class ProjectSignArrange implements Serializable{
    private Integer id;
    private Integer projectSignId;
    private String stationName; //岗位信息
    private String projectName; //项目信息

    private Integer userId;
    private String userName; //用户姓名
    private String telphone;
    private Date startTime;
    private String startDate; //排版开始时间
    private Date endTime;
    private String endDate; //排班结束时间
    private Integer hours; //工作小时数

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public ProjectSignArrange() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProjectSignId() {
        return projectSignId;
    }

    public void setProjectSignId(Integer projectSignId) {
        this.projectSignId = projectSignId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }
}
