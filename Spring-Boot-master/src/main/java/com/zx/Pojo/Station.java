package com.zx.Pojo;

public class Station {
    private Integer id;
    private Integer projectId;
    private String stationName;
    private String stationDesc;
    private String stationConds;
    private Integer planSignNum;
    private Integer SignNum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getStationDesc() {
        return stationDesc;
    }

    public void setStationDesc(String stationDesc) {
        this.stationDesc = stationDesc;
    }

    public String getStationConds() {
        return stationConds;
    }

    public void setStationConds(String stationConds) {
        this.stationConds = stationConds;
    }

    public Integer getPlanSignNum() {
        return planSignNum;
    }

    public void setPlanSignNum(Integer planSignNum) {
        this.planSignNum = planSignNum;
    }

    public Integer getSignNum() {
        return SignNum;
    }

    public void setSignNum(Integer signNum) {
        SignNum = signNum;
    }

    public Station() {
    }
}
