package com.zx.Service;

import com.zx.Dao.UrlClickMapper;
import com.zx.Pojo.UrlClickNum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by root on 2019/1/10
 */
@Service
public class UrlClickService {
    @Autowired
    private UrlClickMapper urlClickMapper;


    public void addNumofClick(UrlClickNum urlClickNum){
        if(checkIsexist(urlClickNum)){
            urlClickMapper.addNum(urlClickNum);
        }else{
            urlClickMapper.addClickItem(urlClickNum);
        }

    }

    public boolean checkIsexist(UrlClickNum urlClickNum){
        return urlClickMapper.checkIsexist(urlClickNum)>0?true:false;
    }
}
