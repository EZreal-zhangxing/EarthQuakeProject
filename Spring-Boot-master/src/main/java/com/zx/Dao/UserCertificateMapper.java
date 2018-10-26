package com.zx.Dao;

import com.zx.Pojo.Pageinfo;
import com.zx.Pojo.UserCertificate;

import java.util.List;

public interface UserCertificateMapper {

    void addUserCertificate(UserCertificate userCertificate);

    UserCertificate getUsercertificate(UserCertificate userCertificate);

    Integer getCountUserCertificate(UserCertificate userCertificate);

    List<UserCertificate> getListUserCertificate(Pageinfo pageinfo);

    void delUserCertificate(Integer id);
}
