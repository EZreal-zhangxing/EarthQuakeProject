package com.zx.Service;

import java.util.List;

import javax.annotation.PostConstruct;

import com.github.pagehelper.PageHelper;
import com.zx.Pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zx.Dao.UserMapper;

@Service
public class UserService {
	@Autowired
	private UserMapper userMapper;
	
	public User Login(User user){
		User userCount=userMapper.getUserNum(user);
		return userCount;
	}

	public Integer getCountOfCommons(Integer userId){ return userMapper.getCountOfCommonsByUserId(userId); }

	public List<Common> getListOfCommons(Pageinfo pageinfo){ return userMapper.getCommonListByuserId(pageinfo); }

	public Integer getCountOfProject(Integer userId) { return userMapper.getCountofProjectByuserid(userId); }

	public List<ProjectSign> getListOfproject(Pageinfo pageinfo){
		List<ProjectSign> list =userMapper.getProjectListByuserId(pageinfo);
		for(ProjectSign projectSign :list){
			projectSign.setHours(userMapper.getServiceHoursByprojectSignId(projectSign.getId()));
		}
		return list;
	}

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

	public User searchUserInfo(User user){ return userMapper.getUserInfoByTel(user);}

	public boolean updateUserCode(User user){
		Integer num=userMapper.updateUserCodeByUserid(user);
		return num>0?true:false;
	}

	public User checkCode(User user){
		User num=userMapper.checkCode(user);
		if(num != null){
			//更新状态为注册成功！
			num.setPassword(user.getPassword());
			userMapper.changeUserStatue(num);
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

	public Integer getCountListofUserApply(UserApply userApply){
		return userMapper.getCountUserApply(userApply);
	}

	public List<UserApply> getListofUserApply(Pageinfo pageinfo){
		List<UserApply> list=userMapper.getAllUserApply(pageinfo);
		for(UserApply userApply:list){
			userApply.setProjectNum(userMapper.getprojectNum(userApply.getUserId()));
			userApply.setServiceHours(userMapper.getServicehours(userApply.getUserId()));
		}
		return list;
	}

	public void deleteApply(Integer id){
		userMapper.deleteApplyByid(id);
	}

	public void updateUserApply(Integer id){
		userMapper.updateStatuByid(id);
	}

	public List<UserApply> getListOfuserApplylist(Integer userId) {
		return userMapper.getUserApplyListById(userId);
	}

	public Integer getCountofMyclass(UserTraning userTraning){
		return userMapper.getMyClassNum(userTraning);
	}

	public List<UserTraning> getlistofMyTraning(Pageinfo pageinfo){
		return userMapper.getMyClass(pageinfo);
	}

	public void saveMyClass(UserTraning userTraning){
		userMapper.addMyclass(userTraning);
	}

	public User getUserByid(Integer id){
		return userMapper.getUserInfobyid(id);
	}

	public void updateUser(User user){
		userMapper.updateUserInfo(user);
	}

	public Integer addUserVolunteer(UserVolunteer userVolunteer){
		return userMapper.insertUserVolunteerInfo(userVolunteer);
	}

	public Integer getCountMessage(Integer userId){
		return userMapper.getCountUserMessage(userId);
	}

	public List<UserMessage> getListOfMessage(Pageinfo pageinfo,Integer userId){
		PageHelper.startPage(pageinfo.getPagenum(),pageinfo.getShownum());
		List<UserMessage> list = userMapper.getUserMessage(userId);
		return list;
	}

	public void updateMessagestatue(Integer messageId){
		userMapper.updateMessageIsread(messageId);
	}

	public Integer addUserOrder(UserOrder userOrder){
		return userMapper.addUserOrder(userOrder);
	}

	public Boolean CheckIsExistRecord(UserOrder userOrder,Integer type){
		if(type == 1){
			//资料下载
			Integer num = userMapper.getIsexistOrder(userOrder);
			return num>0?true:false;
		}else{
			//课件下载
			Integer num = userMapper.getIsexistOrderTraning(userOrder);
			return num>0?true:false;
		}
	}

	public Integer reduceUserScore(UserOrder userOrder){
		return userMapper.reduceUserScore(userOrder);
	}

	public Integer addUserScore(UserOrder userOrder){
		return userMapper.addUserScore(userOrder);
	}

	public AdminUser adminlogin(AdminUser adminUser){
		return userMapper.adminUserlogin(adminUser);
	}

	public void updateAdminCode(AdminUser adminUser){
		userMapper.updateAdminUserLoginCode(adminUser);
	}

	public Integer checkAdminLogin(String id,String token){
		AdminUser adminUser = new AdminUser();
		adminUser.setId(Integer.parseInt(id));
		adminUser.setLoginCode(token);
		return userMapper.checkAdminCode(adminUser);
	}

	public Integer getCountOrder(Integer id){
		return userMapper.getCountUserOrder(id);
	}

	public List<UserOrder> getListOfOrder(Pageinfo pageinfo,Integer id){
		PageHelper.startPage(pageinfo.getPagenum(),pageinfo.getShownum());
		return userMapper.getListUserOrder(id);
	}
}
