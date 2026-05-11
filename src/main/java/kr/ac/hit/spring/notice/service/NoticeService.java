package kr.ac.hit.spring.notice.service;

import java.util.ArrayList;
import java.util.Map;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import kr.ac.hit.spring.notice.model.Notice;

public interface NoticeService {
	
	public int selectNoticeCount(Map<String, Object> map) throws Exception;
	
	public ArrayList<Notice> selectNoticeList(Map<String, Object> map) throws Exception;
	
	public Notice selectNotice(Map<String, Object> condition) throws Exception;
	
	//글 등록
	public int insertNotice(Notice notice, MultipartHttpServletRequest mRequest) throws Exception;
	
	//글 수정
	public int updateNotice(Notice notice, MultipartHttpServletRequest mRequest) throws Exception;
	
	//글 삭제
	public int deleteNotice(Notice notice) throws Exception;
}
