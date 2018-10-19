package com.zx.Service;

import java.util.List;

import javax.annotation.PostConstruct;

import com.zx.Pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zx.Dao.UserMapper;

@Service
public class UserService {
	@Autowired
	private UserMapper userMapper;
	
	public boolean Login(User user){
		Integer userCount=userMapper.getUserNum(user);
		return userCount>0?true:false;
	}

	public Integer getCountOfCommons(Integer userId){ return userMapper.getCountOfCommonsByUserId(userId); }

	public List<Common> getListOfCommons(Pageinfo pageinfo){ return userMapper.getCommonListByuserId(pageinfo); }

	public Integer getCountOfProject(Integer userId) { return userMapper.getCountofProjectByuserid(userId); }

	public List<ProjectSign> getListOfproject(Pageinfo pageinfo){ return userMapper.getProjectListByuserId(pageinfo); }

	@PostConstruct
	public void init_method(){
		System.out.println(this.getClass().getName() + " init");
	}

	public String getUserName(Integer userId){ return userMapper.getUserNameByuserId(userId); }

	public Integer getArrangeCountByUserId(Integer userId) { return userMapper.getArrangeCountByUserId(userId);}

	public List<ProjectSignArrange> getListofArrage(Pageinfo pageinfo){
		return userMapper.getArrangeInfoByUserId(pageinfo);
	}

	public boolean Regist(User user){
		Integer count=userMapper.addUserAccount(user);
		return count>0?true:false;
	}

	public User searchUserInfo(User user){ return userMapper.getUserInfoByTelOrEmail(user);}

	public boolean updateUserCode(User user){
		Integer num=userMapper.updateUserCodeByUserid(user);
		return num>0?true:false;
	}

	public User checkCode(User user){
		User num=userMapper.checkCode(user);
		if(num != null){
			//更新状态为注册成功！
			userMapper.changeUserStatue(num.getId());
		}
		return num;
	}

	public Integer changePassword(User user){ return userMapper.changePassword(user); }

	public Integer getCountOfTeam(Integer userId){
		return userMapper.getUserTeamInfo(userId);
	}

	public  List<TeamSign> getListUserTeamInfo(Pageinfo pageinfo){
		return userMapper.getListUserTeamInfo(pageinfo);
	}

	public void addUserApply(UserApply userApply){
		Integer num = userMapper.checkIsexist(userApply);
		if(num == 0){
			userMapper.insertUserApply(userApply);
		}
	}

	public Integer getCountListofUserApply(Integer status){
		return userMapper.getCountUserApply(status);
	}
	public List<UserApply> getListofUserApply(Pageinfo pageinfo){
		return userMapper.getAllUserApply(pageinfo);
	}
}
