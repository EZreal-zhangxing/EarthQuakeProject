package com.zx.Dao;

import com.zx.Pojo.*;

import java.util.List;

public interface ForhelpMapper {

    void addHelpInfo(ForHelp forHelp);

    Integer getCountHelp();

    List<ForHelp> getListofHelp(Pageinfo pageinfo);

    void delForhelpInfo(Integer id);
}
