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
@RequestMapping(value="/board")
public class BoardController {
	
	@Autowired
	BoardService boardService;
	
	@RequestMapping(value="/boardList")
	public String boardList(
			@RequestParam(value="searchType", required = false, defaultValue = "") String searchType,
			@RequestParam(value="searchWord", required = false, defaultValue = "") String searchWord,
			@RequestParam(value="currentPage", required = false, defaultValue = "1") int currentPage,
			@RequestParam(value="pageSize", required = false, defaultValue = "10") int pageSize,
			@RequestParam(value="boType", required = true) String boType,
			Model model
			) throws Exception{
		
		int pageCount = 5;
		int totalCount = 0;
		
		ArrayList<Board> boardList = null;
		
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
		
		boardList = boardService.selectBoardList(paramMap);
		
		model.addAttribute("boardList", boardList); 
		model.addAttribute("pagingUtil", pagingUtil); 
		
		return "board/boardList";
	}
	
	@RequestMapping(value="/boardView")
	public String boardView(
			@RequestParam(value="seqNo", required = true) int boSeqNo, 
			Model model
			) throws Exception{
		
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("boSeqNo", boSeqNo);
		
		Board board = boardService.selectBoard(condition);
		model.addAttribute("board", board);
		
		
		return "board/boardView";
		
	}
	
	@RequestMapping(value="/boardForm")
	public String boardForm(@RequestParam(value="boSeqNo", required = false, defaultValue = "0") int boSeqNo,
			Model model, HttpSession session
			) throws Exception{
			
		Board board = new Board();
		
		if(boSeqNo != 0) { //0이 아니면 데이터 수정
			HashMap<String, Object> condition = new HashMap<String, Object>();
			condition.put("boSeqNo", boSeqNo);
			
			board = boardService.selectBoard(condition);
		}else {
			//
			Member member = (Member) session.getAttribute("LOGIN_USER");
			board.setBoWriter(member.getMemId());
			board.setBoWriterName(member.getMemName());			
		}
		
		model.addAttribute("board", board);
		
		
		return "board/boardForm"; 
			
	}
	
	//글 등록
	@RequestMapping(value="/boardInsert")
	public String boardInsert(Board board, Model model, MultipartHttpServletRequest mRequest) throws Exception{
		
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
		
		String viewPage = "redirect:/board/boardList?boType=BBS";
		String message = "글이 등록 되었습니다.";
		
		if(isError) {
			message = "글 등록에 실패했습니다.";
			viewPage = "common/message";
		}
		model.addAttribute("isError", message);
		model.addAttribute("message", message);
		
		return viewPage;
		
	}
	
	@RequestMapping(value="/boardUpdate")
	public String updateBoard(Board board, Model model, HttpSession session, MultipartHttpServletRequest mRequest) throws Exception{
		
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
		
		String viewPage = "redirect:/board/boardView?seqNo="+board.getBoSeqNo();
		String message = "정상적으로 수정되었습니다";
		
		if(isError) {
			message = "수정에 실패했습니다.";
			viewPage = "common/message";
			
			model.addAttribute("isError", isError);
			model.addAttribute("message", message);			
		}
		
		return viewPage;	
		
	}
	
	@RequestMapping(value="/boardDelete")
	public String deleteBoard(@RequestParam(value="boSeqNo", required = true) int boSeqNo, 
			Model model, HttpSession session) throws Exception{
		
		boolean isError = false;
		
		try {
			
			Member member = (Member) session.getAttribute("LOGIN_USER");
			
			Board board = new Board();
			board.setBoSeqNo(boSeqNo);
			board.setUpdUser(member.getMemId());
			
			int updCnt = boardService.deleteBoard(board);
			
			if(updCnt == 0) {
				isError = true;
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			isError = true;
			
		}
		
		String viewPage = "redirect:/board/boardList?boType=BBS";
		String message = "삭제에 성공했습니다.";
		
		if(isError) {
			message = "삭제에 실패했습니다.";
			viewPage = "common/message";
			
			model.addAttribute("isError", isError);
			model.addAttribute("message", message);
		}
		
		return viewPage;
		
	}
	
	

}
