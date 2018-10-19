package com.zx.Service;

import com.zx.Dao.NewsMapper;
import com.zx.Pojo.Common;
import com.zx.Pojo.Model;
import com.zx.Pojo.News;
import com.zx.Pojo.Pageinfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsService {
	@Autowired
	private NewsMapper newsMapper;

	public List<News> getListofNews(Pageinfo pageinfo){
		return newsMapper.getListofNewsByCondition(pageinfo);
	}

	public Integer getCountOfNews(News news){
		return newsMapper.getCountOfNews(news);
	}

	public List<Model> getmodelByid(String id) { return newsMapper.getChildModelByid(id);}

	public void delNewsByid(Integer id){
		//删除评论
		newsMapper.deleteNewsCommonsByarticalId(id);
		//删除文章信息
		newsMapper.delNewsByid(id);
	}

	public List<Model> getOneLevelModel(String id){ return newsMapper.getOneLevelModel(id); }

	public Integer saveNews(News news){ return newsMapper.saveNews(news); }

	public Integer updateNews(News news){ return newsMapper.updateNews(news); }

	public News getNewsByid(Integer id){
		News news=newsMapper.getNewsByid(id);
		if(news!=null){
			news.setCommonList(newsMapper.getnewCommons(id));
		}
		return news;
	}

	public Integer addNewsCommon(Common NewsCommon){ return newsMapper.AddNewsCommon(NewsCommon); }

	public List<Common> getListOfNewsCommon(Integer articalId){ return newsMapper.getnewCommons(articalId); }
}
