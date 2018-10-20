package com.zx.Dao;

import com.zx.Pojo.*;

import java.util.List;

public interface PartFileMapper {
    /**
     * 获取文件列表
     * @param pageinfo
     * @return
     */
    List<PartFile> getPartFileList(Pageinfo pageinfo);

    Integer getCountofFIle(PartFile partFile);

    void addFile(PartFile partFile);

    PartFile getFIleByid(Integer id);

    void deleteFileById(Integer id);

    void updateFileByid(PartFile partFile);
}
