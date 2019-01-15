package com.zx.Pojo;

import java.io.Serializable;

/**
 * @author zhangxing
 * @Date 2019/1/15
 */
public class SearchArtical implements Serializable {
    private Integer id;
    private Integer model;
    private String modelName;
    private String title;

    private String RequestUrl;


    public SearchArtical() {
    }

    public String getRequestUrl() {
        return RequestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        RequestUrl = requestUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getModel() {
        return model;
    }

    public void setModel(Integer model) {
        this.model = model;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
