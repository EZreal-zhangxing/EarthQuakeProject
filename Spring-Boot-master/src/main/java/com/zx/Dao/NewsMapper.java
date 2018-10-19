package com.zx.Dao;

import com.zx.Pojo.Common;
import com.zx.Pojo.Model;
import com.zx.Pojo.News;
import com.zx.Pojo.Pageinfo;

import java.util.List;

public interface NewsMapper {
    Integer getCountOfNews(News news);

    List<News> getListofNewsByCondition(Pageinfo pageinfo);

    List<Model> getChildModelByid(String id);

    void delNewsByid(Integer id);

    void deleteNewsCommonsByarticalId(Integer articalId);

    List<Model> getOneLevelModel(String id);

    Integer saveNews(News news);

    News getNewsByid(Integer id);

    Integer updateNews(News news);

    Integer AddNewsCommon(Common newsCommon);

    List<Common> getnewCommons(Integer articalId);
}
