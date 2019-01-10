package com.zx.Pojo;

import java.io.Serializable;

/**
 * Created by zx on 2019/1/10
 */
public class UrlClickNum implements Serializable{
    private Integer id;
    private String modelUrl;
    private String modelDesc;
    private Integer clickNum;

    public UrlClickNum() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModelUrl() {
        return modelUrl;
    }

    public void setModelUrl(String modelUrl) {
        this.modelUrl = modelUrl;
    }

    public String getModelDesc() {
        return modelDesc;
    }

    public void setModelDesc(String modelDesc) {
        this.modelDesc = modelDesc;
    }

    public Integer getClickNum() {
        return clickNum;
    }

    public void setClickNum(Integer clickNum) {
        this.clickNum = clickNum;
    }
}
