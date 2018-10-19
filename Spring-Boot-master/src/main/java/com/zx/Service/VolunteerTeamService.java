package com.zx.Service;

import com.zx.Dao.VolunteerTeamMapper;
import com.zx.Pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VolunteerTeamService {
	@Autowired
	private VolunteerTeamMapper volunteerTeamMapper;

	public List<TeamType> getListofTeamType(){ return volunteerTeamMapper.getListofTeamType(); }

	public List<TeamSerciveType> getListofTeamServiceType(){ return volunteerTeamMapper.getListofTeamServiceType(); }

	public Integer getCountOfVolunteerTeam(VolunteerTeam volunteerTeam){ return volunteerTeamMapper.getCountOfVolunteerTeam(volunteerTeam); }

	public List<VolunteerTeam> getListofVolunteerTeam(Pageinfo pageinfo){ return volunteerTeamMapper.getListofVolunteerTeam(pageinfo); }

	public void saveVolunteerTeam(VolunteerTeam volunteerTeam){ volunteerTeamMapper.saveVolunteerTeam(volunteerTeam); }

	public Integer delVolunteerTeam(Integer id){ return volunteerTeamMapper.delVolunteerTeam(id); }

	public VolunteerTeam getVolunteerTeamByid(Integer id){ return volunteerTeamMapper.getVolunteerTeamByid(id); }

	public List<TeamSign> getListofTeamSign(Integer id){ return volunteerTeamMapper.getListofSignTeam(id); }

	public void addSignTeam(TeamSign teamSign){ volunteerTeamMapper.addSignTeam(teamSign);}
}
