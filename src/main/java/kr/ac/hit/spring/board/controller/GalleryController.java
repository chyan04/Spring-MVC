package kr.ac.hit.spring.board.controller;

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

import kr.ac.hit.spring.board.model.Board;
import kr.ac.hit.spring.board.service.BoardService;
import kr.ac.hit.spring.common.util.PagingUtil;
import kr.ac.hit.spring.member.model.Member;


@Controller
@RequestMapping(value="/gallery")
public class GalleryController {
	
	@Autowired
	BoardService boardService;
	
	@RequestMapping(value="/galleryList")
	public String galleryList(
			@RequestParam(value="searchType", required = false, defaultValue = "") String searchType,
			@RequestParam(value="searchWord", required = false, defaultValue = "") String searchWord,
			@RequestParam(value="currentPage", required = false, defaultValue = "1") int currentPage,
			@RequestParam(value="pageSize", required = false, defaultValue = "10") int pageSize,
			@RequestParam(value="boType", required = true) String boType,
			Model model
			) throws Exception{
		
		int pageCount = 5;
		int totalCount = 0;
		
		ArrayList<Board> galleryList = null;
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		//import org.apache.commons.lang3.StringUtils
		if(!StringUtils.isBlank(searchWord) && !StringUtils.isBlank(searchType)) {
			paramMap.put("searchWord", searchWord);
			paramMap.put("searchType", searchType);
		}
		paramMap.put("boType", boType);
		
		//게시물 총 갯수 구하기
		totalCount = boardService.selectBoardCount(paramMap);
		
		//kr.ac.hit.spring.common 
		PagingUtil pagingUtil = new PagingUtil(currentPage, totalCount, pageSize, pageCount);
		
		paramMap.put("startRow", pagingUtil.getStartRow());
		paramMap.put("endRow", pagingUtil.getEndRow());
		
		galleryList = boardService.selectBoardList(paramMap);
		
		model.addAttribute("galleryList", galleryList); 
		model.addAttribute("pagingUtil", pagingUtil); 
		
		return "board/galleryList";
	}
	
	@RequestMapping(value="/galleryView")
	public String galleryView(
			@RequestParam(value="seqNo", required = true) int boSeqNo, 
			Model model
			) throws Exception{
		
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("boSeqNo", boSeqNo);
		
		Board gallery = boardService.selectBoard(condition);
		model.addAttribute("gallery", gallery);

		return "board/galleryView";
	}
	
	@RequestMapping(value="/galleryForm")
	public String galleryForm(@RequestParam(value="boSeqNo", required = false, defaultValue = "0") int boSeqNo,
			Model model, HttpSession session
			) throws Exception{
			
		Board gallery = new Board();
		
		if(boSeqNo != 0) {
			HashMap<String, Object> condition = new HashMap<String, Object>();
			condition.put("boSeqNo", boSeqNo);
			
			gallery = boardService.selectBoard(condition);
		}else {
			Member member = (Member) session.getAttribute("LOGIN_USER");
			gallery.setBoWriter(member.getMemId());
			gallery.setBoWriterName(member.getMemName());			
		}
		model.addAttribute("gallery", gallery);
				
		return "board/galleryForm"; 
	}

	//글 등록
	@RequestMapping(value="/galleryInsert")
	public String galleryInsert(Board board, Model model, MultipartHttpServletRequest mRequest) throws Exception{
		
		boolean isError = false;
		
		try {
			int updCnt = boardService.insertBoard(board, mRequest);
			
			if(updCnt == 0) {
				isError = true;
			}
				
		}catch(Exception e) {
			e.printStackTrace();
			isError = true;
		}
		
		String viewPage = "redirect:/gallery/galleryList?boType=GALLERY";
		String message = "<갤러리> 등록이 완료 되었습니다.";
		
		if(isError) {
			message = "<갤러리> 등록에 실패 하었습니다.";
			viewPage = "common/message";
		}
		model.addAttribute("isError", message);
		model.addAttribute("message", message);
		
		return viewPage;
		
	}
	
	@RequestMapping(value="/galleryUpdate")
	public String updategallery(Board board, Model model, HttpSession session, MultipartHttpServletRequest mRequest) throws Exception{
		
		boolean isError = false;
		
		try {
			
			Member member = (Member) session.getAttribute("LOGIN_USER");
			board.setUpdUser(member.getMemId());
			
			int updCnt = boardService.updateBoard(board, mRequest);
			
			if(updCnt == 0) {
				isError = true;
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			isError = true;			
		}
		
		String viewPage = "redirect:/gallery/galleryView?seqNo="+board.getBoSeqNo();
		String message = "<갤러리> 수정이 완료 되었습니다.";
		
		if(isError) {
			message = "<갤러리> 수정에 실패 하였습니다.";
			viewPage = "common/message";
			
		}
		model.addAttribute("isError", isError);
		model.addAttribute("message", message);			
		
		return viewPage;	
		
	}
	
	@RequestMapping(value="/galleryDelete")
	public String deleteGallery(@RequestParam(value="boSeqNo", required = true) int boSeqNo, 
			Model model, HttpSession session) throws Exception{
		
		boolean isError = false;
		
		try {
			
			Member member = (Member) session.getAttribute("LOGIN_USER");
			
			Board gallery = new Board();
			gallery.setBoSeqNo(boSeqNo);
			gallery.setUpdUser(member.getMemId());
			
			int updCnt = boardService.deleteBoard(gallery);
			
			if(updCnt == 0) {
				isError = true;
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			isError = true;
			
		}
		
		String viewPage = "redirect:/gallery/galleryList?boType=GALLERY";
		String message = "<갤러리> 삭제가 완료 되었습니다.";
		
		if(isError) {
			message = "<갤러리> 삭제에 실패 하였습니다.";
			viewPage = "common/message";
			
		}
		model.addAttribute("isError", isError);
		model.addAttribute("message", message);
		
		return viewPage;
		
	}
	
	

}
