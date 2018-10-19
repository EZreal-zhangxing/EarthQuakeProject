package com.zx.Dao;

import com.zx.Pojo.Common;
import com.zx.Pojo.Pageinfo;
import com.zx.Pojo.VolunteerPolicy;

import java.util.List;

public interface VolunteerPolicyMapper {
    public Integer getCountofVolunteerPolicy(VolunteerPolicy volunteerPolicy);

    public List<VolunteerPolicy> getListOfVolunteerPolicy(Pageinfo pageinfo);

    public Integer delVolunteerPolicy(Integer id);

    public Integer addVolunteerPolicy(VolunteerPolicy volunteerPolicy);

    public Integer updateVolunteerPolicy(VolunteerPolicy volunteerPolicy);

    public VolunteerPolicy getVolunteerPolicyByid(Integer id);

    public void updateAddViewNum(Integer id); //浏览人数+1
    public void updateAddCommonNum(Integer id); //评论人数+1
    public void updateReduceCommonNum(Integer id); //评论人数-1

    public Integer addPolicyCommon(Common policyCommon);

    public Integer delPolicyCommon(Integer id);

    public void delPolicyCommonByArticalId(Integer articalId);

    public List<Common> getListofPolicyCommon(Integer articalId);
}
