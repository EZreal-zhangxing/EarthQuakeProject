package com.zx.Dao;

import com.zx.Pojo.*;

import java.util.HashMap;
import java.util.List;

public interface VolunteerProjectMapper {
    Integer getCountOfVolunteerProject(VolunteerProject volunteerProject);

    List<VolunteerProject> getListVolunteerProject(Pageinfo pi);

    List<ProjectArea> getListofProjectArea();

    void saveVolunteerProject(VolunteerProject volunteerProject);

    void updateVolunteerProject(VolunteerProject volunteerProject);

    Integer getNextId();

    Integer updateVolunteerProjectStatue(HashMap map);

    Integer delProjectByid(Integer id);

    void delStationByprojectId(Integer projectId);

    void delProjectSignInfo(Integer projectId);

    Integer addProjectSign(ProjectSign projectSign);

    Integer addSignNumByprojectId(ProjectSign projectSign);

    Integer addSignNumBystationId(ProjectSign projectSign);

    Integer delProjectSign(ProjectSign projectSign);

    Integer reduceSignNumByprojectId(ProjectSign projectSign);

    Integer reduceSignNumBystationId(ProjectSign projectSign);

    List<ProjectSign> getListofSign(Integer projectId);

    Integer addProjectStation(Station station);

    void delProjectStationsByprojectId(Integer projectId);

    VolunteerProject getProjectByid(Integer id);

    List<Station> getStationsByProjectId(Integer projectId);

    void saveArrange(ProjectSignArrange projectSignArrange);

    List<ProjectSignArrange> getArrangeInfoByUserId(Integer userId);
}
