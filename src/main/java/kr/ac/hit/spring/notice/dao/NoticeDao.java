package kr.ac.hit.spring.notice.dao;

import java.util.ArrayList;
import java.util.Map;

import kr.ac.hit.spring.notice.model.Notice;

public interface NoticeDao {
	
	public int selectNoticeCount(Map<String, Object> map) throws Exception;
	
	// 목록조회, List or ArrayList
	public ArrayList<Notice> selectNoticeList(Map<String, Object> map) throws Exception;
	
	//한건 조회
	public Notice selectNotice(Map<String, Object> condition) throws Exception;
	
	//조회 수 증가
	public int updateNoticeHitCnt(Map<String, Object> condition) throws Exception;
	
	//등록
	public int insertNotice(Notice notice) throws Exception;
	
	//수정
	public int updateNotice(Notice notice) throws Exception;
	
	//삭제
	public int deleteNotice(Notice notice) throws Exception;
}
