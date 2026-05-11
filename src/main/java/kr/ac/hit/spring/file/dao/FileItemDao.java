package kr.ac.hit.spring.file.dao;

import java.util.ArrayList;
import java.util.HashMap;

import kr.ac.hit.spring.file.model.FileItem;

public interface FileItemDao {
	// 첨푸파일 목록 조회
	public ArrayList<FileItem> selectFileItemList(HashMap<String, Object> paramMap) throws Exception;
	
	// 1건 조회
	public FileItem selectFileItem(HashMap<String, Object> paramMap) throws Exception;
	
	// 업로드 내용 저장
	public int insertFileItem(FileItem fileItem) throws Exception;
	
	// 삭제
	public int deleteFileItem(HashMap<String, Object> paramMap) throws Exception;
}