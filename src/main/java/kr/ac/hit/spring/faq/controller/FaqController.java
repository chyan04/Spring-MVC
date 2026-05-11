package kr.ac.hit.spring.faq.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import kr.ac.hit.spring.faq.Faq;
import kr.ac.hit.spring.faq.service.FaqService;
import kr.ac.hit.spring.common.util.PagingUtil;
import kr.ac.hit.spring.member.model.Member;

@Controller
@RequestMapping(value="/faq")
public class FaqController {
	
	@Autowired
	FaqService faqService;
	
	@RequestMapping(value="/faqList")
	public String faqList(
			@RequestParam(value="searchType", required = false, defaultValue = "") String searchType,
			@RequestParam(value="searchWord", required = false, defaultValue = "") String searchWord,
			@RequestParam(value="currentPage", required = false, defaultValue = "1") int currentPage,
			@RequestParam(value="pageSize", required = false, defaultValue = "10") int pageSize,
			Model model
			) throws Exception{
		
		int pageCount = 5;
		int totalCount = 0;
		
		ArrayList<Faq> faqList = null;
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		if(!StringUtils.isBlank(searchWord) && !StringUtils.isBlank(searchType)) {
			paramMap.put("searchWord", searchWord);
			paramMap.put("searchType", searchType);
		}
		
		//게시물 총 갯수 구하기
		totalCount = faqService.selectFaqCount(paramMap);
		
		//kr.ac.hit.spring.common 
		PagingUtil pagingUtil = new PagingUtil(currentPage, totalCount, pageSize, pageCount);
		
		paramMap.put("startRow", pagingUtil.getStartRow());
		paramMap.put("endRow", pagingUtil.getEndRow());
		
		faqList = faqService.selectFaqList(paramMap);
		
		model.addAttribute("faqList", faqList); 
		model.addAttribute("pagingUtil", pagingUtil); 
		
		return "faq/faqList";
	}
	
	@RequestMapping(value="/faqView")
	public String faqView(
			@RequestParam(value="seqNo", required = true) int faqSeqNo, 
			Model model
			) throws Exception{

		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("faqSeqNo", faqSeqNo);
		
		Faq faq = faqService.selectFaq(condition);
		model.addAttribute("faq", faq);
		
		return "faq/faqView";	
	}
	
	@RequestMapping(value="/faqForm")
	public String faqForm(@RequestParam(value="faqSeqNo", required = false, defaultValue = "0") int faqSeqNo,
			Model model, HttpSession session
			) throws Exception{
			
		Faq faq = new Faq();
		
		//0이 아니면 데이터 수정
		if(faqSeqNo != 0) { 		
			HashMap<String, Object> condition = new HashMap<String, Object>();
			condition.put("faqSeqNo", faqSeqNo);
			
			faq = faqService.selectFaq(condition);
		}else {
			Member member = (Member) session.getAttribute("LOGIN_USER");
			faq.setFaqWriter(member.getMemId());
			faq.setFaqWriterName(member.getMemName());			
		} 
		model.addAttribute("faq", faq);
		return "faq/faqForm"; 
	}
	//글 등록
	@RequestMapping(value="/faqInsert")
	public String faqInsert(Faq faq, Model model, MultipartHttpServletRequest mRequest) throws Exception{
		boolean isError = false;
		try {
			int updCnt = faqService.insertFaq(faq, mRequest);
			
			if(updCnt == 0) {
				isError = true;
			}
				
		}catch(Exception e) {
			e.printStackTrace();
			isError = true;
		}
		
		String viewPage = "common/message";
		String message = "<FAQ> 등록이 완료 되었습니다";
		
		if(isError) {
			message = "<FAQ> 등록이 실패 했습니다";
			viewPage = "common/message";
		}
		model.addAttribute("isError", isError);
		model.addAttribute("message", message);
		return viewPage;	
	}
	
	@RequestMapping(value="/faqUpdate")
	public String updateFaq(Faq faq, Model model, HttpSession session, MultipartHttpServletRequest mRequest) throws Exception{
		
		boolean isError = false;
		
		try {
			
			Member member = (Member) session.getAttribute("LOGIN_USER");
			faq.setFaqUpUser(member.getMemId());
			
			int updCnt = faqService.updateFaq(faq, mRequest);
			
			if(updCnt == 0) {
				isError = true;
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			isError = true;			
		}

		String viewPage = "redirect:/faq/faqView?seqNo="+faq.getFaqSeqNo();
		String message = "<FAQ> 수정이 완료 되었습니다";

		if(isError) {
			message = "<FAQ> 수정에 실패 하였습니다";
			viewPage = "common/message";
		}
		model.addAttribute("isError", isError);
		model.addAttribute("message", message);			
		return viewPage;	
	}
	
	// 삭제 
	@RequestMapping(value="/faqDelete")
	public String deleteFaq(@RequestParam(value="faqSeqNo", required = true) int faqSeqNo, 
			Model model, HttpSession session) throws Exception{
		
		boolean isError = false;
		
		try {
			
			Member member = (Member) session.getAttribute("LOGIN_USER");
			
			Faq faq = new Faq();
			faq.setFaqSeqNo(faqSeqNo);
			faq.setFaqUpUser(member.getMemId());
			
			int updCnt = faqService.deleteFaq(faq);
			
			if(updCnt == 0) {
				isError = true;
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			isError = true;
			
		}
		
		String viewPage = "common/message";
		String message = "<FAQ> 삭제가 완료 되었습니다";
		
		if(isError) {
			message = "<FAQ> 삭제에 실패 했습니다";
			viewPage = "common/message";
			
		}
		model.addAttribute("isError", isError);
		model.addAttribute("message", message);
		return viewPage;
	}
}