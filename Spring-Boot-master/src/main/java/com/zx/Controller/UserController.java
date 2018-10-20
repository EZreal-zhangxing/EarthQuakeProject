package com.zx.Controller;

import com.zx.Pojo.*;
import com.zx.Util.EncoderHandler;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import com.zx.Service.UserService;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;

@Api(tags = "用户模块")
@RequestMapping("user")
@RestController
public class UserController extends BaseController{
	Logger logger=Logger.getLogger(UserController.class);
	@Autowired
	public UserService userService;

	@Autowired
	private EncoderHandler encoderHandler;
	@Value("${linux_icon}")
	private String LINUX_PATH;

	@Value("${windows_icon}")
	private String WIN_PATH;

	@RequestMapping("/hello")
	public String HelloMethod(){
		return "hello world linux path "+ LINUX_PATH +" windows path "+ WIN_PATH +" encoderHandler class is "+ encoderHandler;
	}

	/**
	 * 用户登陆接口
	 * @param userId
	 * @param password
	 * @return
	 */
	@ApiOperation(value = "用户登陆接口" ,response = Message.class,httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "userId", value = "用户ID",required = true,dataType = "int"),
			@ApiImplicitParam(name = "password", value = "用户密码",required = true,dataType = "String")
	})
	@RequestMapping("/login")
	public Message Login(@RequestParam(value="userId") Integer userId,@RequestParam(value="password") String password){
		User user=new User();
		user.setId(userId);
		user.setPassword(password);
		boolean result=userService.Login(user);
		return result?new Message(MessageCode.MSG_SUCCESS):new Message(MessageCode.MSG_FAIL);
	}

	/**
	 * 发送验证码 生成账户 默认密码为123456
	 * @param telphone
	 * @param email
	 * @param type 1 电话 2 邮箱
	 * @return
	 */
	@ApiOperation(value = "发送验证码接口" ,response = Message.class,httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "telphone", value = "电话号码",required = false,dataType = "String"),
			@ApiImplicitParam(name = "email", value = "邮箱",required = false,dataType = "String"),
			@ApiImplicitParam(name = "type", value = "类型(1 表示电话 2表示邮箱)",dataType = "int")
	})
	@RequestMapping("/sendRegistCode")
	public Message regist(@RequestParam(value = "telphone",required = false) String telphone,
						  @RequestParam(value = "email",required = false) String email,
						  @RequestParam(value = "type") Integer type){
		User user=new User();
		user.setPassword(encoderHandler.encodeByMD5("123456"));
		user.setStatue(0);//设置状态注册中
		String code=getCode();
		user.setCode(code);
		if(type == 1){
			//电话
			user.setUserName(telphone);
			user.setTelphone(telphone);
			User userinfo=userService.searchUserInfo(user);
			if(userinfo == null){
				//保存信息
				userService.Regist(user);
			}else{
				userinfo.setCode(code);
				userService.updateUserCode(userinfo);
			}
			//调用发送接口

		}else{
			//邮箱
			user.setUserName(email);
			user.setUserEmail(email);
			User userinfo=userService.searchUserInfo(user);
			if(userinfo == null){
				//保存信息
				userService.Regist(user);
			}else{
				userinfo.setCode(code);
				userService.updateUserCode(userinfo);
			}
			//调用发送接口
		}

		return new Message(MessageCode.MSG_SUCCESS_CODE);
	}

	/**
	 * 验证码校验
	 * 返回用户信息
	 * @return
	 */
	@ApiOperation(value = "验证码教研",notes = "成功返回用户信息对象，失败返回消息错误对象",response = Object.class,httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "telphone", value = "电话号码",required = false,dataType = "String"),
			@ApiImplicitParam(name = "email", value = "邮箱",required = false,dataType = "String"),
			@ApiImplicitParam(name = "code", value = "验证码",dataType = "String")
	})
	@RequestMapping("/checkCode")
	public Object checkCode(@RequestParam(value = "telphone",required = false) String telphone,
							 @RequestParam(value = "email",required = false) String email,
							 @RequestParam(value = "code") String code){
		User user=new User();
		user.setTelphone(telphone);
		user.setUserEmail(email);
		user.setCode(code);
		User res=userService.checkCode(user);
		if(res != null){
			//校验成功
			return res;
		}else{
			return new Message(MessageCode.MSG_CODE_ERROR);
		}
	}

	/**
	 * 修改密码
	 * @param userId
	 * @param password
	 * @return
	 */
	@ApiOperation(value = "密码修改",response = Object.class,httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "userId", value = "用户ID",dataType = "int"),
			@ApiImplicitParam(name = "password", value = "密码",dataType = "String")
	})
	@RequestMapping("/changePassword")
	public Message changePassword(@RequestParam(value = "userId") Integer userId,
								  @RequestParam(value = "password") String password){
		User user=new User();
		user.setId(userId);
		user.setPassword(encoderHandler.encodeByMD5(password));
		Integer num=userService.changePassword(user);
		if(num>0){
			return new Message(MessageCode.MSG_SUCCESS);
		}else{
			return new Message(MessageCode.MSG_FAIL);
		}
	}

	/**
	 * 用户头像修改
	 * @param fileicon
	 * @return
	 */
	@ApiOperation(value = "头像修改",response = Message.class,httpMethod = "POST")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "headfilepath", value = "头像修改（此处为表单提交enctype=\"multipart/form-data\"）",dataType = "MultipartFile")
	})
	@RequestMapping("/changeHeadIcon")
	public Message changeHeadIcon(@RequestParam(value = "headfilepath") MultipartFile fileicon){
		String fileName=fileicon.getOriginalFilename();
		String hzname=fileName.substring(fileName.indexOf("."), fileName.length());
		String md5filename=encoderHandler.encodeByMD5(fileName+System.currentTimeMillis());
		File file=new File(getfilepath()+md5filename+hzname);
		try {
			saveFile(file,fileicon.getInputStream(),getfilepath());
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("上传失败！",e);
			return new Message(MessageCode.MSG_FAIL);
		}
		return new Message(MessageCode.MSG_SUCCESS);
	}

	/**
	 * 获取用户的评论信息 分页
	 * @param userId
	 * @param pageSize
	 * @param page
	 * @return
	 */
	@ApiOperation(value = "获取用户得评论信息",response = Pageinfo.class,httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "userId",required = true, value = "用户ID",dataType = "int"),
			@ApiImplicitParam(name = "pageSize", value = "页面条数",defaultValue = "10", dataType = "int"),
			@ApiImplicitParam(name = "page", value = "标题",defaultValue = "1", dataType = "string")
	})
	@RequestMapping("/getListOfCommonByUserId")
	public Pageinfo getListOfCommon(@RequestParam(value = "userId",required = true) Integer userId,
									@RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize,
									@RequestParam(value = "page",defaultValue = "1") String page){
		Common common=new Common();
		common.setUserId(userId);
		Integer num=userService.getCountOfCommons(userId);
		Pageinfo pageinfo=initpage(num,(page!=null && !"".equals(page))?page:"1",pageSize!=null?pageSize:10);
		pageinfo.setConditionCommon(common);
		List<Common> result=userService.getListOfCommons(pageinfo);
			pageinfo.setResult(result);
		return pageinfo;
	}

	/**
	 * 我得项目/岗位
	 * @param userId
	 * @param pageSize
	 * @param page
	 * @return
	 */
	@ApiOperation(value = "获取用户得项目/岗位信息",response = Pageinfo.class,httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "userId",required = true, value = "用户ID",dataType = "int"),
			@ApiImplicitParam(name = "pageSize", value = "页面条数",defaultValue = "10", dataType = "int"),
			@ApiImplicitParam(name = "page", value = "标题",defaultValue = "1", dataType = "string")
	})
	@RequestMapping("/getListOfProjectByUserId")
	public Pageinfo getListOfProject(@RequestParam(value = "userId",required = true) Integer userId,
									 @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize,
									 @RequestParam(value = "page",defaultValue = "1") String page){
		Integer num=userService.getCountOfProject(userId);
		Pageinfo pageinfo=initpage(num,page,pageSize);
		ProjectSign projectSign=new ProjectSign();
		projectSign.setUserId(userId);
		pageinfo.setConditionProjectSign(projectSign);
		List<ProjectSign> list=userService.getListOfproject(pageinfo);
		pageinfo.setResult(list);
		return pageinfo;
	}

	/**
	 * 获取我得排班信息
	 * @return
	 */
	@ApiOperation(value = "获取用户得排班信息",response = Pageinfo.class,httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "userId",required = true, value = "用户ID",dataType = "int"),
			@ApiImplicitParam(name = "pageSize", value = "页面条数",defaultValue = "10", dataType = "int"),
			@ApiImplicitParam(name = "page", value = "标题",defaultValue = "1", dataType = "string")
	})
	@RequestMapping("/getListOfArrange")
	public Pageinfo getListOfArrange(@RequestParam(value = "userId",required = true) Integer userId,
									 @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize,
									 @RequestParam(value = "page",defaultValue = "1") String page){
		Integer count=userService.getArrangeCountByUserId(userId);
		Pageinfo pageinfo=initpage(count,page,pageSize);
		ProjectSign projectSign=new ProjectSign();
		projectSign.setUserId(userId);
		pageinfo.setConditionProjectSign(projectSign);
		List<ProjectSignArrange> list = userService.getListofArrage(pageinfo);
		pageinfo.setResult(list);
		return pageinfo;
	}

	/**
	 * 获取用户所属团体信息
	 * @param userId
	 * @return
	 */
	@ApiOperation(value = "获取用户所属团体信息",response = Pageinfo.class,httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "userId",required = true, value = "用户ID",dataType = "int"),
			@ApiImplicitParam(name = "pageSize", value = "页面条数",defaultValue = "10", dataType = "int"),
			@ApiImplicitParam(name = "page", value = "标题",defaultValue = "1", dataType = "string")
	})
	@RequestMapping("/getListOfTeam")
	public Pageinfo getListOfTeam(@RequestParam(value = "userId",required = true) Integer userId,
								  @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize,
								  @RequestParam(value = "page",defaultValue = "1") String page){
		Integer num=userService.getCountOfTeam(userId);
		Pageinfo pageinfo=initpage(num,page,pageSize);
		TeamSign teamSign=new TeamSign();
		teamSign.setUserId(userId);
		pageinfo.setConditionTeamSign(teamSign);
		List<TeamSign> list=userService.getListUserTeamInfo(pageinfo);
		pageinfo.setResult(list);
		return pageinfo;
	}

	@ApiOperation(value = "更具用户ID (考核模块使用)获取该用户所申请的星级列表",response = UserApply.class,httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "userId",required = true, value = "用户ID",dataType = "int")
	})
	@RequestMapping("/getListofUserApply/{userId}")
	public List<UserApply> getListofUserApply(@PathVariable(value = "userId") Integer userId){
		return userService.getListOfuserApplylist(userId);
	}


	/**
	 * 添加用户申请星级信息
	 * @param userId
	 * @param apply
	 * @return
	 */
	@ApiOperation(value = "添加用户申请星级信息",response = Message.class,httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "userId",required = true, value = "用户ID",dataType = "int"),
			@ApiImplicitParam(name = "apply", value = "申请类型 1 一星 2 二星 3三星 4 四星 5 五星",dataType = "int")
	})
	@RequestMapping("/userAddApply")
	public Message userAddApply(@RequestParam(value = "userId") Integer userId,
								@RequestParam(value = "apply") Integer apply){
		UserApply userApply=new UserApply();
		userApply.setUserId(userId);
		userApply.setApplyType(apply);
		userService.addUserApply(userApply);
		return new Message(MessageCode.MSG_SUCCESS);
	}

	/**
	 * 获取用户申请列表
	 * @param statue
	 * @param pageSize
	 * @param page
	 * @return
	 */
	@RequestMapping("/getListofApply")
	public Pageinfo getListofApply(@RequestParam(value = "username",required = false) String name,
								   @RequestParam(value = "statue" ,defaultValue = "-1") Integer statue,
								   @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize,
								   @RequestParam(value = "page",defaultValue = "1") String page){
		UserApply userApply=new UserApply();
		userApply.setUserName(name);
		userApply.setStatus(statue);
		Integer num=userService.getCountListofUserApply(userApply);
		Pageinfo pageinfo=initpage(num,page,pageSize);
		pageinfo.setConditionUserApply(userApply);
		List<UserApply> list=userService.getListofUserApply(pageinfo);
		pageinfo.setResult(list);
		return pageinfo;
	}

	@RequestMapping("/deleteUserApply/{id}")
	public Message deleteUserApply(@PathVariable(value = "id") Integer id){
		userService.deleteApply(id);
		return new Message(MessageCode.MSG_SUCCESS);
	}

	@RequestMapping("/applyPass/{id}")
	public Message applyPass(@PathVariable(value = "id") Integer id){
		userService.updateUserApply(id);
		return new Message(MessageCode.MSG_SUCCESS);
	}


	@ApiOperation(value = "获取用户看过的视频列表",response = Pageinfo.class,httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "userId",required = true, value = "用户ID",dataType = "int"),
			@ApiImplicitParam(name = "type", value = "0 未完成 1 已完成",dataType = "int")
	})
	@RequestMapping("/getListofMyclass")
	public Pageinfo getListofMyclass(@RequestParam(value = "userId") Integer userId,
									 @RequestParam(value = "type") Integer type,
									 @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize,
									 @RequestParam(value = "page",defaultValue = "1") String page){
		UserTraning userTraning = new UserTraning();
		userTraning.setUserId(userId);
		userTraning.setType(type);
		Integer num=userService.getCountofMyclass(userTraning);
		Pageinfo pageinfo=initpage(num,page,pageSize);
		pageinfo.setConditionUserTraning(userTraning);
		List<UserTraning> list = userService.getlistofMyTraning(pageinfo);
		pageinfo.setResult(list);
		return pageinfo;
	}

	/**
	 * 增加用户看过的视频信息
	 * @param userId
	 * @param type
	 * @param traningId
	 * @param haveSeeTime
	 * @return
	 */
	@ApiOperation(value = "获取用户看过的视频列表",response = Message.class,httpMethod = "GET")
	@ApiImplicitParams({
					@ApiImplicitParam(name = "userId",required = true, value = "用户ID",dataType = "int"),
					@ApiImplicitParam(name = "type", value = "0 未完成 1 已完成",dataType = "int"),
					@ApiImplicitParam(name = "traningId",required = true,value = "视频文章ID",dataType = "int"),
					@ApiImplicitParam(name = "haveSeeTime",required = false, value = "已看时间",dataType = "String")
	})
	@RequestMapping("/AddMyClass")
	public Message AddMyClass(@RequestParam(value = "userId",required = true) Integer userId,
							  @RequestParam(value = "type",required = true) Integer type,
							  @RequestParam(value = "traningId",required = true) Integer traningId,
							  @RequestParam(value = "haveSeeTime",required = false) String haveSeeTime){
		UserTraning userTraning=new UserTraning();
		userTraning.setUserId(userId);
		userTraning.setType(type);
		userTraning.setTraningId(traningId);
		userTraning.setHavewatchTime(haveSeeTime);
		userService.saveMyClass(userTraning);
		return new Message(MessageCode.MSG_SUCCESS);
	}

	/**
	 * 获取当前路径下的系统环境
	 * @return
	 */
	public String getfilepath()
	{
		String sysStr=System.getProperties().getProperty("os.name");
		if(sysStr.indexOf("Windows") != -1)
		{
			return WIN_PATH;
		}else
		{
			return LINUX_PATH;
		}
	}

	/**
	 * 生成验证码
	 * @return
	 */
	public String getCode(){
		return (int) (Math.random()*10000%10000)+"";
	}
}
