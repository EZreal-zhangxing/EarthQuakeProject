package com.zx.Pojo;

public class Model {
    private Integer id;
    private String modelName;
    private String parentModel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getParentModel() {
        return parentModel;
    }

    public void setParentModel(String parentModel) {
        this.parentModel = parentModel;
    }

    public Model(Integer id, String modelName, String parentModel) {
        this.id = id;
        this.modelName = modelName;
        this.parentModel = parentModel;
    }

    public Model() {
    }
}
