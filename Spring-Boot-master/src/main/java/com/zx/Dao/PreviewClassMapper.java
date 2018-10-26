package com.zx.Dao;


import com.zx.Pojo.Pageinfo;
import com.zx.Pojo.PreviewClass;

import java.util.LinkedList;
import java.util.List;

public interface PreviewClassMapper {

    void BatchInsertPreviewClass(LinkedList<PreviewClass> linkedList);

    Integer getCountPreviewClass();

    List<PreviewClass> getListofPreviewClass(Pageinfo pageinfo);

    void delPreviewClassById(Integer id);
}
