package com.zx.Dao;

import com.zx.Pojo.*;

import java.util.List;

public interface VolunteerTeamMapper {
    List<TeamType> getListofTeamType();

    List<TeamSerciveType> getListofTeamServiceType();

    Integer getCountOfVolunteerTeam(VolunteerTeam volunteerTeam);

    List<VolunteerTeam> getListofVolunteerTeam(Pageinfo pageinfo);

    void saveVolunteerTeam(VolunteerTeam volunteerTeam);

    Integer delVolunteerTeam(Integer id);

    VolunteerTeam getVolunteerTeamByid(Integer id);

    void addSignTeam(TeamSign teamSign);

    List<TeamSign> getListofSignTeam(Integer id);
}
