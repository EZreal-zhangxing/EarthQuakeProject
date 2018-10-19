package com.zx.Pojo;

/**
 * 用户点评 志愿之星pojo类
 */
public class Common {
    private Integer id;
    private Integer type; //1:新闻评论，2 志愿故事评论 3 志愿之心评论 4 志愿政策
    private Integer articalId;
    private Integer userId;
    private String userName;
    private String content;
    private String createDate;

    private String title;
    private String description;
    private String modelName;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public Integer getArticalId() {
        return articalId;
    }

    public void setArticalId(Integer articalId) {
        this.articalId = articalId;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Common() {
    }
}
