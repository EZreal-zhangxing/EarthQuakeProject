package com.zx.Pojo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author zhangxing
 * @Date 2018/10/25 16:06
 */
public class UserVolunteer implements Serializable {
    @ApiModelProperty(value = "用户ID")
    private Integer userId;
    @ApiModelProperty(value = "国家地区")
    private String country;
    @ApiModelProperty(value = "真实姓名")
    private String trueName;
    @ApiModelProperty(value = "证件号码")
    private String credentialNo;
    @ApiModelProperty(value = "性别")
    private String sex;
    @ApiModelProperty(value = "生日")
    private String birthday;
    @ApiModelProperty(value = "政治面貌")
    private String policyStatue;
    @ApiModelProperty(value = "民族")
    private String nation;
    @ApiModelProperty(value = "籍贯")
    private String nativePlace;
    @ApiModelProperty(value = "固定电话")
    private String phone;
    @ApiModelProperty(value = "电话")
    private String telphone;
    @ApiModelProperty(value = "居住区域")
    private String liveArea;
    @ApiModelProperty(value = "地址")
    private String address;
    @ApiModelProperty(value = "最高学历")
    private String educationBackground;
    @ApiModelProperty(value = "从业状况")
    private String workStatue;
    @ApiModelProperty(value = "是否受邀加入志愿项目")
    private String isInvitaion;
    @ApiModelProperty(value = "服务区域")
    private String serviceArea;
    @ApiModelProperty(value = "服务类型")
    private String serviceType;
    @ApiModelProperty(value = "志愿者类型（多个逗号分隔）")
    private String volunteerType;

    public String getVolunteerType() {
        return volunteerType;
    }

    public void setVolunteerType(String volunteerType) {
        this.volunteerType = volunteerType;
    }

    public UserVolunteer() {
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public String getCredentialNo() {
        return credentialNo;
    }

    public void setCredentialNo(String credentialNo) {
        this.credentialNo = credentialNo;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPolicyStatue() {
        return policyStatue;
    }

    public void setPolicyStatue(String policyStatue) {
        this.policyStatue = policyStatue;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getLiveArea() {
        return liveArea;
    }

    public void setLiveArea(String liveArea) {
        this.liveArea = liveArea;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEducationBackground() {
        return educationBackground;
    }

    public void setEducationBackground(String educationBackground) {
        this.educationBackground = educationBackground;
    }

    public String getWorkStatue() {
        return workStatue;
    }

    public void setWorkStatue(String workStatue) {
        this.workStatue = workStatue;
    }

    public String getIsInvitaion() {
        return isInvitaion;
    }

    public void setIsInvitaion(String isInvitaion) {
        this.isInvitaion = isInvitaion;
    }

    public String getServiceArea() {
        return serviceArea;
    }

    public void setServiceArea(String serviceArea) {
        this.serviceArea = serviceArea;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }
}
