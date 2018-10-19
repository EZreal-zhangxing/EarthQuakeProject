package com.zx.Dao;

import com.zx.Pojo.*;

import java.util.List;

public interface VolunteerMapper {
    Integer getCountByCondition(Volunteer volunteer);

    List<Volunteer> getListofVolunteer(Pageinfo pageinfo);

    void delVolunteerByid(Integer id);

    void delVolunteerCommonsByarticalId(Integer articalId);

    Integer addVolunteerInfo(Volunteer volunteer);

    Volunteer getVolunteerByid(Integer id);

    Integer updateVolunteer(Volunteer volunteer);

    Integer addVolunteerCommon(Common volunteerCommon);

    List<Common> getvolunteerCommons(Integer articalId);
}
