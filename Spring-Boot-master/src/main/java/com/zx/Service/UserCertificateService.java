package com.zx.Service;

import com.zx.Dao.UserCertificateMapper;
import com.zx.Pojo.Pageinfo;
import com.zx.Pojo.UserCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserCertificateService {
	@Autowired
	private UserCertificateMapper userCertificateMapper;

	public void saveUserCertificate(UserCertificate userCertificate){
		userCertificateMapper.addUserCertificate(userCertificate);
	}

	public UserCertificate getUserCerificate(UserCertificate userCertificate){
		return userCertificateMapper.getUsercertificate(userCertificate);
	}

	public Integer getCount(UserCertificate userCertificate){
		return userCertificateMapper.getCountUserCertificate(userCertificate);
	}

	public List<UserCertificate> getList(Pageinfo pageinfo){
		return userCertificateMapper.getListUserCertificate(pageinfo);
	}

	public void delUserCertificate(Integer id){
		userCertificateMapper.delUserCertificate(id);
	}
}
