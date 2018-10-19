package com.zx.Service;

import com.sun.org.apache.bcel.internal.generic.INEG;
import com.zx.Dao.VolunteerPolicyMapper;
import com.zx.Dao.VolunteerStarMapper;
import com.zx.Pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VolunteerPolicyService {
	@Autowired
	private VolunteerPolicyMapper volunteerPolicyMapper;

	public Integer getCountOfVolunteerPolicy(VolunteerPolicy volunteerPolicy){
		return volunteerPolicyMapper.getCountofVolunteerPolicy(volunteerPolicy);
	}

	public List<VolunteerPolicy> getListofVolunteerPolicy(Pageinfo pageinfo){
		return volunteerPolicyMapper.getListOfVolunteerPolicy(pageinfo);
	}

	public Integer delVolunteerPolicyByid(Integer id){
		//删除所有评论
		volunteerPolicyMapper.delPolicyCommonByArticalId(id);
		//删除文章信息
		return volunteerPolicyMapper.delVolunteerPolicy(id);
	}

	public Integer addVolunteerPolicy(VolunteerPolicy volunteerPolicy){ return volunteerPolicyMapper.addVolunteerPolicy(volunteerPolicy); }

	public Integer updateVolunteerPolicy(VolunteerPolicy volunteerPolicy){ return volunteerPolicyMapper.updateVolunteerPolicy(volunteerPolicy); }

	public VolunteerPolicy getVpObjectByid(Integer id){ return volunteerPolicyMapper.getVolunteerPolicyByid(id); }

	//前端用
	public VolunteerPolicy getVolunteerPolicyByid(Integer id){
		volunteerPolicyMapper.updateAddViewNum(id);
		VolunteerPolicy volunteerPolicy=volunteerPolicyMapper.getVolunteerPolicyByid(id);
		List<Common> list=volunteerPolicyMapper.getListofPolicyCommon(id);
		volunteerPolicy.setPolicyCommonList(list);
		return volunteerPolicy;
	}

	public Integer addPolicyCommon(Common policyCommon){
		volunteerPolicyMapper.updateAddCommonNum(policyCommon.getArticalId());
		return volunteerPolicyMapper.addPolicyCommon(policyCommon);
	}

	public Integer delPolicyCommon(Integer id,Integer articalId){
	    volunteerPolicyMapper.updateReduceCommonNum(articalId);
	    return volunteerPolicyMapper.delPolicyCommon(id);
    }

    public List<Common> getListofPolicyCommon(Integer articalId){
		return volunteerPolicyMapper.getListofPolicyCommon(articalId);
	}
}
