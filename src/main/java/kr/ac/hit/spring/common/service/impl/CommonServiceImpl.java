package kr.ac.hit.spring.common.service.impl;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.ac.hit.spring.common.service.CommonService;
import kr.ac.hit.spring.file.dao.FileItemDao;
import kr.ac.hit.spring.file.model.FileItem;

@Service
public class CommonServiceImpl implements CommonService  {
	@Autowired
	FileItemDao fileItemDao;
	
	@Override
	public FileItem getFileItem(HashMap<String, Object> paramMap) throws Exception{
		return fileItemDao.selectFileItem(paramMap);
	}
} 