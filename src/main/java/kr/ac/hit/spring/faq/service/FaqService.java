package kr.ac.hit.spring.faq.service;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.ac.hit.spring.faq.Faq;

public interface FaqService {
	
	public int selectFaqCount(Map<String, Object> map) throws Exception;
	
	public ArrayList<Faq> selectFaqList(Map<String, Object> map) throws Exception;
	
	public Faq selectFaq(Map<String, Object> condition) throws Exception;
	
	//글 등록
	public int insertFaq(Faq faq, MultipartHttpServletRequest mRequest) throws Exception;
	
	//글 수정
	public int updateFaq(Faq faq, MultipartHttpServletRequest mRequest) throws Exception;
	
	//글 삭제
	public int deleteFaq(Faq faq) throws Exception;
}
