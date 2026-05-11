package kr.ac.hit.spring.common.service;

import java.util.HashMap;

import kr.ac.hit.spring.file.model.FileItem;

public interface CommonService {
	public FileItem getFileItem(HashMap<String, Object> paramMap) throws Exception;
} 