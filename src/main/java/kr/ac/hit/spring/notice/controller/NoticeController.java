package kr.ac.hit.spring.notice.controller;

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
import kr.ac.hit.spring.notice.model.Notice;
import kr.ac.hit.spring.notice.service.NoticeService;
import kr.ac.hit.spring.common.util.PagingUtil;
import kr.ac.hit.spring.member.model.Member;

@Controller
@RequestMapping(value="/notice")
public class NoticeController {
	
	@Autowired
	NoticeService noticeService;
	
	@RequestMapping(value="/noticeList")
	public String noticeList(
			@RequestParam(value="searchType", required = false, defaultValue = "") String searchType,
			@RequestParam(value="searchWord", required = false, defaultValue = "") String searchWord,
			@RequestParam(value="currentPage", required = false, defaultValue = "1") int currentPage,
			@RequestParam(value="pageSize", required = false, defaultValue = "10") int pageSize,
			Model model
			) throws Exception{
		
		int pageCount = 5;
		int totalCount = 0;
		
		ArrayList<Notice> noticeList = null;
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		if(!StringUtils.isBlank(searchWord) && !StringUtils.isBlank(searchType)) {
			paramMap.put("searchWord", searchWord);
			paramMap.put("searchType", searchType);
		}
		
		//게시물 총 갯수 구하기
		totalCount = noticeService.selectNoticeCount(paramMap);
		
		//kr.ac.hit.spring.common 
		PagingUtil pagingUtil = new PagingUtil(currentPage, totalCount, pageSize, pageCount);
		
		paramMap.put("startRow", pagingUtil.getStartRow());
		paramMap.put("endRow", pagingUtil.getEndRow());
		
		noticeList = noticeService.selectNoticeList(paramMap);
		
		model.addAttribute("noticeList", noticeList); 
		model.addAttribute("pagingUtil", pagingUtil); 
		
		return "notice/noticeList";
	}
	
	@RequestMapping(value="/noticeView")
	public String noticeView(
			@RequestParam(value="seqNo", required = true) int noticeSeqNo, 
			Model model
			) throws Exception{

		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("noticeSeqNo", noticeSeqNo);
		
		Notice notice = noticeService.selectNotice(condition);
		model.addAttribute("notice", notice);
		
		return "notice/noticeView";	
	}
	
	@RequestMapping(value="/noticeForm")
	public String noticeForm(@RequestParam(value="noticeSeqNo", required = false, defaultValue = "0") int noticeSeqNo,
			Model model, HttpSession session
			) throws Exception{
			
		Notice notice = new Notice();
		
		//0이 아니면 데이터 수정
		if(noticeSeqNo != 0) { 		
			HashMap<String, Object> condition = new HashMap<String, Object>();
			condition.put("boSeqNo", noticeSeqNo);
			
			notice = noticeService.selectNotice(condition);
		}else {
			Member member = (Member) session.getAttribute("LOGIN_USER");
			notice.setNoticeWriter(member.getMemId());
			notice.setNoticeWriterName(member.getMemName());			
		} 
		model.addAttribute("notice", notice);
		return "notice/noticeForm"; 
			
	}
	
	//글 등록
	@RequestMapping(value="/noticeInsert")
	public String noticeInsert(Notice notice, Model model, MultipartHttpServletRequest mRequest) throws Exception{
		boolean isError = false;
		try {
			int updCnt = noticeService.insertNotice(notice, mRequest);
			
			if(updCnt == 0) {
				isError = true;
			}
				
		}catch(Exception e) {
			e.printStackTrace();
			isError = true;
		}
		
		String viewPage = "common/message";
		String message = "<공지사항> 등록이 완료 되었습니다";
		
		if(isError) {
			message = "<공지사항> 등록이 실패 했습니다";
			viewPage = "common/message";
		}
		model.addAttribute("isError", isError);
		model.addAttribute("message", message);
		return viewPage;	
	}
	
	@RequestMapping(value="/noticeUpdate")
	public String updateNotice(Notice notice, Model model, HttpSession session, MultipartHttpServletRequest mRequest) throws Exception{
		
		boolean isError = false;
		
		try {
			
			Member member = (Member) session.getAttribute("LOGIN_USER");
			notice.setNoticeUpUser(member.getMemId());
			
			int updCnt = noticeService.updateNotice(notice, mRequest);
			
			if(updCnt == 0) {
				isError = true;
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			isError = true;			
		}

		String viewPage = "redirect:/notice/noticeView?seqNo="+notice.getNoticeSeqNo();
		String message = "<공지사항> 수정이 완료 되었습니다";

		if(isError) {
			message = "<공지사항> 수정에 실패 하였습니다";
			viewPage = "common/message";
		}
		model.addAttribute("isError", isError);
		model.addAttribute("message", message);	
		
		return viewPage;	
	}
	
	// 삭제 
	@RequestMapping(value="/noticeDelete")
	public String deleteNotice(@RequestParam(value="noticeSeqNo", required = true) int noticeSeqNo, 
			Model model, HttpSession session) throws Exception{
		
		boolean isError = false;
		
		try {
			
			Member member = (Member) session.getAttribute("LOGIN_USER");
			
			Notice notice = new Notice();
			notice.setNoticeSeqNo(noticeSeqNo);
			notice.setNoticeUpUser(member.getMemId());
			
			int updCnt = noticeService.deleteNotice(notice);
			
			if(updCnt == 0) {
				isError = true;
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			isError = true;
			
		}
		
		String viewPage = "common/message";
		String message = "<공지사항> 삭제가 완료 되었습니다";
		
		if(isError) {
			message = "<공지사항> 삭제에 실패 했습니다";
			viewPage = "common/message";
			
		}
		model.addAttribute("isError", isError);
		model.addAttribute("message", message);
		
		return viewPage;
	}
}