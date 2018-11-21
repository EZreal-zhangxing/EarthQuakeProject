package com.zx.Dao;

import java.util.List;

import com.zx.Pojo.*;

public interface UserMapper{
	User getUserNum(User user);

	List<Common> getCommonListByuserId(Pageinfo pageinfo);

	Integer getCountOfCommonsByUserId(Integer userId);

	List<ProjectSign> getProjectListByuserId(Pageinfo pageinfo);

	Integer getCountofProjectByuserid(Integer userId);

	String getUserNameByuserId(Integer id);

	Integer getArrangeCountByUserId(Integer id);

	List<ProjectSignArrange> getArrangeInfoByUserId(Pageinfo pageinfo);

	Integer addUserAccount(User user);

	User getUserInfoByTel(User user);

	Integer updateUserCodeByUserid(User user);

	User checkCode(User user);

	void changeUserStatue(User user);

	Integer changePassword(User user);

	Integer getUserTeamInfo(Integer userId);

	List<TeamSign> getListUserTeamInfo(Pageinfo pageinfo);

	void insertUserApply(UserApply userApply);

	Integer checkIsexist(UserApply userApply);

	/**
	 * 用户ID 获取对应的盛情信息
	 * @param userId
	 * @return
	 */
	List<UserApply> getUserApplyById(String userId);

	/**
	 * 更具装填获取
	 * @param pageinfo
	 * @return
	 */
	List<UserApply> getAllUserApply(Pageinfo pageinfo);

	Integer getCountUserApply(UserApply userApply);

	/**
	 * 更具userId获取用户项目书
	 * @param userId
	 * @return
	 */
	Integer getprojectNum(Integer userId);

	List<UserTraning> checkIsExistColl(UserTraning userTraning);

	void delUserTraning(Integer id);
	/**
	 * 更具用户ID获取
	 * @param userId
	 * @return
	 */
	Integer getServicehours(Integer userId);

	void deleteApplyByid(Integer id);

	void updateStatuByid(Integer id);

	List<UserApply> getUserApplyListById(Integer userId);

	Integer getMyClassNum(UserTraning userTraning);

	List<UserTraning> getMyClass(Pageinfo pageinfo);

	void addMyclass(UserTraning userTraning);

	Integer getServiceHoursByprojectSignId(Integer id);

	User getUserInfobyid(Integer id);

	void updateUserInfo(User user);

	Integer insertUserVolunteerInfo(UserVolunteer userVolunteer);

	List<UserMessage> getUserMessage(Integer userId);

	Integer getCountUserMessage(Integer userId);

	void updateMessageIsread(Integer messageId);

	Integer addUserOrder(UserOrder userOrder);

	Integer getIsexistOrder(UserOrder userOrder);

	Integer getIsexistOrderTraning(UserOrder userOrder);

	Integer reduceUserScore(UserOrder userOrder);

	Integer addUserScore(UserOrder userOrder);

	AdminUser adminUserlogin(AdminUser adminUser);

	void updateAdminUserLoginCode(AdminUser adminUser);

	Integer checkAdminCode(AdminUser adminUser);

	Integer getCountUserOrder(Integer userId);

	List<UserOrder> getListUserOrder(Integer userId);

	Integer checkUserCommitInfo(Integer userId);
}
