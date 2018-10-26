package com.zx.Pojo;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhangxing
 * @Date 2018/10/10 9:41
 */
public class OnlineTraning implements Serializable {
    private Integer id;
    private Integer type; //课程类型
    private String outUrl; //文章外链

    private String title; //课程标题
    private String imageUrl; //展示图片连接
    private String videoUrl; //视频连接
    private String teacherDesc; //教师介绍
    private String teacherDescVideo; //讲师视频介绍
    private String description; //课程介绍
    private String outline; //提纲
    private String classfileDesc;//资料描述
    private String classfile; //资料连接
    private Integer readNum;
    private Integer collectionNum;

    private Integer isFavorite;

    private String createDate;

    private List<TraningQuestion> questionList;

    public Integer getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(Integer isFavorite) {
        this.isFavorite = isFavorite;
    }

    public List<TraningQuestion> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<TraningQuestion> questionList) {
        this.questionList = questionList;
    }

    public String getOutUrl() {
        return outUrl;
    }

    public void setOutUrl(String outUrl) {
        this.outUrl = outUrl;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getTeacherDesc() {
        return teacherDesc;
    }

    public void setTeacherDesc(String teacherDesc) {
        this.teacherDesc = teacherDesc;
    }

    public String getTeacherDescVideo() {
        return teacherDescVideo;
    }

    public void setTeacherDescVideo(String teacherDescVideo) {
        this.teacherDescVideo = teacherDescVideo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOutline() {
        return outline;
    }

    public void setOutline(String outline) {
        this.outline = outline;
    }

    public String getClassfileDesc() {
        return classfileDesc;
    }

    public void setClassfileDesc(String classfileDesc) {
        this.classfileDesc = classfileDesc;
    }

    public String getClassfile() {
        return classfile;
    }

    public void setClassfile(String classfile) {
        this.classfile = classfile;
    }

    public Integer getReadNum() {
        return readNum;
    }

    public void setReadNum(Integer readNum) {
        this.readNum = readNum;
    }

    public Integer getCollectionNum() {
        return collectionNum;
    }

    public void setCollectionNum(Integer collectionNum) {
        this.collectionNum = collectionNum;
    }

    public OnlineTraning() {
    }
}
