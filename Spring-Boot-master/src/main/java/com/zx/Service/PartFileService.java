package com.zx.Service;

import com.zx.Dao.NewsMapper;
import com.zx.Dao.PartFileMapper;
import com.zx.Pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartFileService {
	@Autowired
	private PartFileMapper fileMapper;
	public Integer getCountFilelist(){
		return fileMapper.getCountofFIle();
	}

	public List<PartFile> getFilelist(Pageinfo pageinfo){
		return fileMapper.getPartFileList(pageinfo);
	}

	public void savepartFile(PartFile partFile){
		fileMapper.addFile(partFile);
	}


	public PartFile getFIleByid(Integer id){
		return fileMapper.getFIleByid(id);
	}

	public void deletefileByid(Integer id){
		fileMapper.deleteFileById(id);
	}
}
