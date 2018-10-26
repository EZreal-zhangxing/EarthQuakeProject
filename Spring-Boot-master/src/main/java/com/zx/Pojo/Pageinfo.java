package com.zx.Pojo;

import java.io.Serializable;

public class Pageinfo implements Serializable{
    private Integer firstpage;//第一页
    private Integer prev;//上一页
    private Integer next;//下一页
    private Integer pagenum;//当前页
    private Integer allpage;//总页面
    private Integer lastpage;//最后一页
    private Integer shownum;//每页展示数
    private String[] pageStr;
    private int begin;
    private int end;

    private News conditionNews;
    private Volunteer conditionVolunteer;
    private VolunteerProject conditionVolunteerProject;
    private VolunteerTeam conditionVolunteerTeam;
    private VolunteerStar conditionVolunteerStar;
    private VolunteerPolicy conditionVolunteerPolicy;
    private Common conditionCommon;
    private ProjectSign conditionProjectSign;
    private TeamSign conditionTeamSign;
    private OnlineTraning conditionOnlineTraning;
    private UserApply conditionUserApply;
    private PartFile conditionFile;
    private UserTraning conditionUserTraning;
    private UserCertificate conditionUC;

    private Object result;

    public UserCertificate getConditionUC() {
        return conditionUC;
    }

    public void setConditionUC(UserCertificate conditionUC) {
        this.conditionUC = conditionUC;
    }

    public UserTraning getConditionUserTraning() {
        return conditionUserTraning;
    }

    public void setConditionUserTraning(UserTraning conditionUserTraning) {
        this.conditionUserTraning = conditionUserTraning;
    }

    public PartFile getConditionFile() {
        return conditionFile;
    }

    public void setConditionFile(PartFile conditionFile) {
        this.conditionFile = conditionFile;
    }

    public UserApply getConditionUserApply() {
        return conditionUserApply;
    }

    public void setConditionUserApply(UserApply conditionUserApply) {
        this.conditionUserApply = conditionUserApply;
    }

    public OnlineTraning getConditionOnlineTraning() {
        return conditionOnlineTraning;
    }

    public void setConditionOnlineTraning(OnlineTraning conditionOnlineTraning) {
        this.conditionOnlineTraning = conditionOnlineTraning;
    }

    public TeamSign getConditionTeamSign() {
        return conditionTeamSign;
    }

    public void setConditionTeamSign(TeamSign conditionTeamSign) {
        this.conditionTeamSign = conditionTeamSign;
    }

    public Integer getFirstpage() {
        return firstpage;
    }

    public void setFirstpage(Integer firstpage) {
        this.firstpage = firstpage;
    }

    public Integer getPrev() {
        return prev;
    }

    public void setPrev(Integer prev) {
        this.prev = prev;
    }

    public Integer getNext() {
        return next;
    }

    public void setNext(Integer next) {
        this.next = next;
    }

    public Integer getPagenum() {
        return pagenum;
    }

    public void setPagenum(Integer pagenum) {
        this.pagenum = pagenum;
    }

    public Integer getAllpage() {
        return allpage;
    }

    public void setAllpage(Integer allpage) {
        this.allpage = allpage;
    }

    public Integer getLastpage() {
        return lastpage;
    }

    public void setLastpage(Integer lastpage) {
        this.lastpage = lastpage;
    }

    public Integer getShownum() {
        return shownum;
    }

    public void setShownum(Integer shownum) {
        this.shownum = shownum;
    }

    public String[] getPageStr() {
        return pageStr;
    }

    public void setPageStr(String[] pageStr) {
        this.pageStr = pageStr;
    }

    public int getBegin() {
        return begin;
    }

    public void setBegin(int begin) {
        this.begin = begin;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public News getConditionNews() {
        return conditionNews;
    }

    public void setConditionNews(News conditionNews) {
        this.conditionNews = conditionNews;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Volunteer getConditionVolunteer() {
        return conditionVolunteer;
    }

    public void setConditionVolunteer(Volunteer conditionVolunteer) {
        this.conditionVolunteer = conditionVolunteer;
    }

    public VolunteerProject getConditionVolunteerProject() {
        return conditionVolunteerProject;
    }

    public void setConditionVolunteerProject(VolunteerProject conditionVolunteerProject) {
        this.conditionVolunteerProject = conditionVolunteerProject;
    }

    public VolunteerTeam getConditionVolunteerTeam() {
        return conditionVolunteerTeam;
    }

    public void setConditionVolunteerTeam(VolunteerTeam conditionVolunteerTeam) {
        this.conditionVolunteerTeam = conditionVolunteerTeam;
    }

    public VolunteerStar getConditionVolunteerStar() {
        return conditionVolunteerStar;
    }

    public void setConditionVolunteerStar(VolunteerStar conditionVolunteerStar) {
        this.conditionVolunteerStar = conditionVolunteerStar;
    }

    public VolunteerPolicy getConditionVolunteerPolicy() {
        return conditionVolunteerPolicy;
    }

    public void setConditionVolunteerPolicy(VolunteerPolicy conditionVolunteerPolicy) {
        this.conditionVolunteerPolicy = conditionVolunteerPolicy;
    }

    public Common getConditionCommon() {
        return conditionCommon;
    }

    public void setConditionCommon(Common conditionCommon) {
        this.conditionCommon = conditionCommon;
    }

    public ProjectSign getConditionProjectSign() {
        return conditionProjectSign;
    }

    public void setConditionProjectSign(ProjectSign conditionProjectSign) {
        this.conditionProjectSign = conditionProjectSign;
    }

    public Pageinfo() {
    }
}