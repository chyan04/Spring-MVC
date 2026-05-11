package kr.ac.hit.spring.faq.dao;

import java.util.ArrayList;
import java.util.Map;
import kr.ac.hit.spring.faq.Faq;

public interface FaqDao {
	
	public int selectFaqCount(Map<String, Object> map) throws Exception;
	
	// 목록조회, List or ArrayList
	public ArrayList<Faq> selectFaqList(Map<String, Object> map) throws Exception;
	
	//한건 조회
	public Faq selectFaq(Map<String, Object> condition) throws Exception;
	
	//조회 수 증가
	public int updateFaqHitCnt(Map<String, Object> condition) throws Exception;
	
	//등록
	public int insertFaq(Faq faq) throws Exception;
	
	//수정
	public int updateFaq(Faq faq) throws Exception;
	
	//삭제
	public int deleteFaq(Faq faq) throws Exception;
}