package com.zx.Pojo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author zhangxing
 * @Date 2018/10/19 12:22
 */
public class UserApply implements Serializable {
    private Integer id;
    private Integer userId;
    private String userName;
    private String userPhone;
    private Integer applyType;
    private String applyName;
    @ApiModelProperty(value = "0 未通过 1 已通过")
    private Integer status;

    private Integer serviceHours; //用户服务时长
    private Integer projectNum; //参与项目数量

    private String createDate;

    public Integer getProjectNum() {
        return projectNum;
    }

    public void setProjectNum(Integer projectNum) {
        this.projectNum = projectNum;
    }

    public Integer getServiceHours() {
        return serviceHours;
    }

    public void setServiceHours(Integer serviceHours) {
        this.serviceHours = serviceHours;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public UserApply() {
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getApplyType() {
        return applyType;
    }

    public String getApplyName() {
        return applyName;
    }

    public void setApplyName(String applyName) {
        this.applyName = applyName;
    }

    public void setApplyType(Integer applyType) {
        if(applyType == 1){
            this.applyName="一星志愿者";
        }else if (applyType == 2){
            this.applyName="二星志愿者";
        }else if (applyType == 3){
            this.applyName="三星志愿者";
        }else if (applyType == 4){
            this.applyName="四星志愿者";
        }else if (applyType == 5){
            this.applyName="五星志愿者";
        }
        this.applyType = applyType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
