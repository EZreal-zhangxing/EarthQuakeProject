package com.zx.Dao;

import com.zx.Pojo.SearchArtical;
import com.zx.Pojo.UrlClickNum;

import java.util.List;

public interface UrlClickMapper {
    Integer checkIsexist(UrlClickNum urlClickNum);

    void addClickItem(UrlClickNum urlClickNum);

    void addNum(UrlClickNum urlClickNum);

    List<SearchArtical> searchNews(String keyword);
    List<SearchArtical> searchVolunteer(String keyword);
    List<SearchArtical> searchVolunteerTeam(String keyword);
    List<SearchArtical> searchVolunteerProject(String keyword);
    List<SearchArtical> searchVolunteerStar(String keyword);
    List<SearchArtical> searchVolunteerPolicy(String keyword);
}
