package com.zx.Service;

import com.zx.Dao.NewsMapper;
import com.zx.Dao.VolunteerMapper;
import com.zx.Pojo.Common;
import com.zx.Pojo.Pageinfo;
import com.zx.Pojo.Volunteer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class VolunteerService {
	@Autowired
	private VolunteerMapper volunteerMapper;

	public Integer getNumofVolunteer(Volunteer volunteer){ return volunteerMapper.getCountByCondition(volunteer); }

	public List<Volunteer> getListofVolunteer(Pageinfo pageinfo){ return volunteerMapper.getListofVolunteer(pageinfo); }

	public void delVolunteerInfo(Integer id){
		volunteerMapper.delVolunteerCommonsByarticalId(id);
		volunteerMapper.delVolunteerByid(id);
	}

	public Integer saveVolunteerInfo(Volunteer volunteer){ return volunteerMapper.addVolunteerInfo(volunteer); }

	public Volunteer getVolunteerByid(Integer id){
		Volunteer volunteer=volunteerMapper.getVolunteerByid(id);
		volunteer.setCommonList(volunteerMapper.getvolunteerCommons(id));
		return volunteer;
	}

	public Integer updateVolunteer(Volunteer volunteer){ return volunteerMapper.updateVolunteer(volunteer); }

	public Integer addVolunteerCommon(Common volunteerCommon){ return volunteerMapper.addVolunteerCommon(volunteerCommon);}

	public List<Common> getListofCommons(Integer articalId){ return volunteerMapper.getvolunteerCommons(articalId); }
}
