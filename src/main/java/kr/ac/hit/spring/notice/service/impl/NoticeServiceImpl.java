package kr.ac.hit.spring.notice.service.impl;

import java.util.ArrayList;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import kr.ac.hit.spring.notice.dao.NoticeDao;
import kr.ac.hit.spring.notice.model.Notice;
import kr.ac.hit.spring.notice.service.NoticeService;

@Service
public class NoticeServiceImpl implements NoticeService {

	@Autowired
	NoticeDao noticeDao;

	@Override
	public int selectNoticeCount(Map<String, Object> map) throws Exception {
		return noticeDao.selectNoticeCount(map);
	}

	@Override
	public ArrayList<Notice> selectNoticeList(Map<String, Object> map) throws Exception {
		return noticeDao.selectNoticeList(map);
	}

	@Transactional(isolation = Isolation.READ_COMMITTED)
	@Override 
	public Notice selectNotice(Map<String, Object> condition) throws Exception {
		//게시글 조회 수 증가
		noticeDao.updateNoticeHitCnt(condition);
				
		//게시글 상세 조회(1건 조회)
		Notice notice = noticeDao.selectNotice(condition);

		return notice;
	}

	@Transactional
	@Override
	public int insertNotice(Notice notice, MultipartHttpServletRequest mRequest) throws Exception {
		//게시글 저장 및 파일정보 저장
		int updCnt = noticeDao.insertNotice(notice);
		
		return updCnt;
	}

	@Transactional
	@Override
	public int updateNotice(Notice notice, MultipartHttpServletRequest mRequest) throws Exception {

		try {
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return noticeDao.updateNotice(notice);
	}

	@Override
	public int deleteNotice(Notice notice) throws Exception {

		return noticeDao.deleteNotice(notice);
	}

}