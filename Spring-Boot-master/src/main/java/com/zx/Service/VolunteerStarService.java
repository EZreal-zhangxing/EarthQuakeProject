package com.zx.Service;

import com.zx.Dao.VolunteerStarMapper;
import com.zx.Pojo.Common;
import com.zx.Pojo.Pageinfo;
import com.zx.Pojo.VolunteerStar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VolunteerStarService {
	@Autowired
	private VolunteerStarMapper volunteerStarMapper;

	public Integer getNumOfVolunteerStar(VolunteerStar volunteerStar) {
		return volunteerStarMapper.getCountOfVolunteerStar(volunteerStar);
	}

	public List<VolunteerStar> getListOfVolunteerStar(Pageinfo pageinfo) {
		return volunteerStarMapper.getListOfVolunteerStar(pageinfo);
	}

	public Integer delVolunteerStar(Integer id) {
		//删除评论
		volunteerStarMapper.delStarCommonsByarticalId(id);
		//删除文章信息
		return volunteerStarMapper.delVolunteerStar(id);
	}

	public VolunteerStar getVolunteerStarByid(Integer id) {
		//获取对象信息
		VolunteerStar volunteerStar=volunteerStarMapper.getVolunteerStarByid(id);
		//获取评论信息
		volunteerStar.setCommons(volunteerStarMapper.getStarCommon(id));
		volunteerStarMapper.updateAddViewNumByid(id);
		return volunteerStar;

	}

	public void addVolunteerStar(VolunteerStar volunteerStar){
		volunteerStarMapper.addVolunteerStar(volunteerStar);
	}

	public List<VolunteerStar> getListofVolunteerbyModelid(Integer modelId) {
		return volunteerStarMapper.getListofVolunteerBymodelId(modelId);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Integer addStarCommon(Common starCommon){
		int num=volunteerStarMapper.addStarCommon(starCommon);
		volunteerStarMapper.updateAddCommonNumByid(starCommon.getArticalId()); //评论数+1
		return num;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Integer delStarCommon(Integer id,Integer articalId){
		int num=volunteerStarMapper.delStarCommonByid(id);
		volunteerStarMapper.updateReduceCommonNumByid(articalId);
		return num;
	}

	public List<Common> getListOfStarCommon(Integer articalId){
		return volunteerStarMapper.getStarCommon(articalId);
	}
}
