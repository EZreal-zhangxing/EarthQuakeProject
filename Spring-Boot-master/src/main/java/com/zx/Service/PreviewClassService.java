package com.zx.Service;

import com.zx.Dao.PreviewClassMapper;
import com.zx.Pojo.Pageinfo;
import com.zx.Pojo.PreviewClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;


@Service
public class PreviewClassService {
	@Autowired
	private PreviewClassMapper previewClassMapper;

	public void BatchInsertPreviewClass(LinkedList<PreviewClass> linkedList){
		previewClassMapper.BatchInsertPreviewClass(linkedList);
	}

	public Integer getNumofClass(){
		return previewClassMapper.getCountPreviewClass();
	}

	public List<PreviewClass> getListofClass(Pageinfo pageinfo){
		return previewClassMapper.getListofPreviewClass(pageinfo);
	}

	public void delPreviewClass(Integer id){
		previewClassMapper.delPreviewClassById(id);
	}
}
