package com.zx.Service;

import com.zx.Dao.ForhelpMapper;
import com.zx.Dao.NewsMapper;
import com.zx.Pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ForhelpService {
	@Autowired
	private ForhelpMapper forhelpMapper;

	public void addForhelpInfo(ForHelp forHelp){
		forhelpMapper.addHelpInfo(forHelp);
	}

	public Integer getNumofHelpInfo(){
		return forhelpMapper.getCountHelp();
	}

	public List<ForHelp> getListofHelp(Pageinfo pageinfo){
		return forhelpMapper.getListofHelp(pageinfo);
	}

	public void delForhelp(Integer id){
		forhelpMapper.delForhelpInfo(id);
	}
}

