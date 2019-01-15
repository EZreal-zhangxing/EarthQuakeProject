package com.zx.Dao;

import com.zx.Pojo.UrlClickNum;

public interface UrlClickMapper {
    Integer checkIsexist(UrlClickNum urlClickNum);

    void addClickItem(UrlClickNum urlClickNum);

    void addNum(UrlClickNum urlClickNum);
}
