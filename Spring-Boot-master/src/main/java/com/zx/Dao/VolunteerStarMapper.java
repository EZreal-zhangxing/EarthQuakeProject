package com.zx.Dao;

import com.zx.Pojo.*;

import java.util.List;

public interface VolunteerStarMapper {
    public Integer getCountOfVolunteerStar(VolunteerStar volunteerStar);

    public List<VolunteerStar> getListOfVolunteerStar(Pageinfo pageinfo);

    public Integer delVolunteerStar(Integer id);

    public void delStarCommonsByarticalId(Integer articalId);

    public VolunteerStar getVolunteerStarByid(Integer id);

    public void addVolunteerStar(VolunteerStar volunteerStar);

    public List<VolunteerStar> getListofVolunteerBymodelId(Integer modelId);

    public Integer addStarCommon(Common starCommon);

    public Integer delStarCommonByid(Integer id);

    public List<Common> getStarCommon(Integer articalId);

    VolunteerStar getVolunteerStartInfoByid(Integer id); //更具ID获取志愿之星的内容

    Integer updateVolunteerStar(VolunteerStar volunteerStar); //更新志愿之心

    public void updateAddViewNumByid(Integer articalId); //浏览数+1
    public void updateReduceViewNumByid(Integer articalId); //浏览数-1
    public void updateAddCommonNumByid(Integer articalId); //评论数+1
    public void updateReduceCommonNumByid(Integer articalId); //评论数-1
}
