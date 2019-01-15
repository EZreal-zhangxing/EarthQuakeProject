package com.zx.Service;

import com.zx.Dao.UrlClickMapper;
import com.zx.Pojo.SearchArtical;
import com.zx.Pojo.UrlClickNum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 2019/1/10
 */
@Service
public class UrlClickService {
    @Autowired
    private UrlClickMapper urlClickMapper;


    public void addNumofClick(UrlClickNum urlClickNum){
        if(checkIsexist(urlClickNum)){
            urlClickMapper.addNum(urlClickNum);
        }else{
            urlClickMapper.addClickItem(urlClickNum);
        }

    }

    public boolean checkIsexist(UrlClickNum urlClickNum){
        return urlClickMapper.checkIsexist(urlClickNum)>0?true:false;
    }

    /**
     * 检索新闻
     * @param keyword
     * @return
     */
    public List<SearchArtical> SearchNews(String keyword){
        ArrayList<SearchArtical> list=new ArrayList<SearchArtical>();
        list.addAll(urlClickMapper.searchNews(keyword));
        list.addAll(urlClickMapper.searchVolunteer(keyword));
        list.addAll(urlClickMapper.searchVolunteerPolicy(keyword));
        list.addAll(urlClickMapper.searchVolunteerProject(keyword));
        list.addAll(urlClickMapper.searchVolunteerStar(keyword));
        list.addAll(urlClickMapper.searchVolunteerTeam(keyword));
        return list;
    }
}
