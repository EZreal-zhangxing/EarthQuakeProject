package com.zx.Pojo;

import java.io.Serializable;

/**
 * @author zhangxing
 * @Date 2018/10/19 13:39
 */
public class PartFile implements Serializable {
    private Integer id;
    private String fileName;
    private String filePath;
    private String createDate;
    private Integer downloadScore;

    public PartFile() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Integer getDownloadScore() {
        return downloadScore;
    }

    public void setDownloadScore(Integer downloadScore) {
        this.downloadScore = downloadScore;
    }
}
