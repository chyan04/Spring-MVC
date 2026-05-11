package kr.ac.hit.spring.faq.service.impl;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.ac.hit.spring.faq.service.FaqService;
import kr.ac.hit.spring.faq.dao.FaqDao;
import kr.ac.hit.spring.faq.Faq;

@Service
public class FaqServiceImpl implements FaqService {

	@Autowired
	FaqDao faqDao;

	@Override
	public int selectFaqCount(Map<String, Object> map) throws Exception {
		return faqDao.selectFaqCount(map);
	}

	@Override
	public ArrayList<Faq> selectFaqList(Map<String, Object> map) throws Exception {
		return faqDao.selectFaqList(map);
	}

	@Transactional(isolation = Isolation.READ_COMMITTED)
	@Override 
	public Faq selectFaq(Map<String, Object> condition) throws Exception {
		//게시글 조회 수 증가
		faqDao.updateFaqHitCnt(condition);
				
		// 게시글 상세 조회(1건 조회)
		Faq faq = faqDao.selectFaq(condition);

		return faq;
	}

	@Transactional
	@Override
	// 게시글 업로드
	public int insertFaq(Faq faq, MultipartHttpServletRequest mRequest) throws Exception {

		int updCnt = faqDao.insertFaq(faq);
		
		return updCnt;
	}

	@Transactional
	@Override
	// 게시글 수정
	public int updateFaq(Faq faq, MultipartHttpServletRequest mRequest) throws Exception {

		try {
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return faqDao.updateFaq(faq);
	}

	// 게시글 삭제
	@Override
	public int deleteFaq(Faq faq) throws Exception {

		return faqDao.deleteFaq(faq);
	}

}