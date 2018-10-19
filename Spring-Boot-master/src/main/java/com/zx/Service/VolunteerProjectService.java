package com.zx.Service;

import com.zx.Dao.VolunteerProjectMapper;
import com.zx.Pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
public class VolunteerProjectService {
	@Autowired
	private VolunteerProjectMapper volunteerProjectMapper;

	public Integer getNumOfvolunteer(VolunteerProject volunteerProject){
		return volunteerProjectMapper.getCountOfVolunteerProject(volunteerProject);
	}

	public List<VolunteerProject> getListVolunteerProject(Pageinfo pageinfo){
		return volunteerProjectMapper.getListVolunteerProject(pageinfo);
	}

	public List<ProjectArea> getListofArea(){ return volunteerProjectMapper.getListofProjectArea();}

	public void saveVolunteerProject(VolunteerProject volunteerProject){ volunteerProjectMapper.saveVolunteerProject(volunteerProject); }

	public void updateVolunteerProject(VolunteerProject volunteerProject){ volunteerProjectMapper.updateVolunteerProject(volunteerProject); }

	public Integer getNextIdOfVp(){ return volunteerProjectMapper.getNextId(); }

	public Integer updateStatue(HashMap map){ return volunteerProjectMapper.updateVolunteerProjectStatue(map); }

	@Transactional(propagation = Propagation.REQUIRED)
	public Integer delProject(Integer id){
		//删除报名信息
		volunteerProjectMapper.delProjectSignInfo(id);
		//删除岗位信息
		volunteerProjectMapper.delStationByprojectId(id);
		//删除项目信息
		return volunteerProjectMapper.delProjectByid(id);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void addProjectSign(ProjectSign projectSign){
		volunteerProjectMapper.addProjectSign(projectSign);
		volunteerProjectMapper.addSignNumByprojectId(projectSign);
		volunteerProjectMapper.addSignNumBystationId(projectSign);
	}
	@Transactional(propagation = Propagation.REQUIRED)
	public void cancelSign(ProjectSign projectSign){
		volunteerProjectMapper.delProjectSign(projectSign);//删除报名信息
		volunteerProjectMapper.reduceSignNumByprojectId(projectSign);//报名数量-1
		volunteerProjectMapper.reduceSignNumBystationId(projectSign);//报名数量-1
	}

	public List<ProjectSign> getProjectList(Integer projectId){
		return volunteerProjectMapper.getListofSign(projectId);
	}

	public Integer addProjectStation(Station station){
		return volunteerProjectMapper.addProjectStation(station);
	}

	public void delProjectStations(Integer projectId){ volunteerProjectMapper.delProjectStationsByprojectId(projectId); }
	public VolunteerProject getProjectInfoByid(Integer id){
		//志愿项目基本信息
		VolunteerProject baseInfo=volunteerProjectMapper.getProjectByid(id);
		//岗位信息
		List<Station> stations=volunteerProjectMapper.getStationsByProjectId(id);
		baseInfo.setStations(stations);
		return baseInfo;
	}

	public List<Station> getStationsByprojectId(Integer id){
		return volunteerProjectMapper.getStationsByProjectId(id);
	}

	public void saveSignArrange(ProjectSignArrange projectSignArrange){ volunteerProjectMapper.saveArrange(projectSignArrange); }

	public List<ProjectSignArrange> getListOfArrange(Integer userId) {
		return volunteerProjectMapper.getArrangeInfoByUserId(userId);
	}
}
