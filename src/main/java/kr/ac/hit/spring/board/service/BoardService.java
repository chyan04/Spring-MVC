package kr.ac.hit.spring.board.service;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.ac.hit.spring.board.model.Board;

public interface BoardService {
	
	public int selectBoardCount(Map<String, Object> map) throws Exception;
	
	public ArrayList<Board> selectBoardList(Map<String, Object> map) throws Exception;
	
	public Board selectBoard(Map<String, Object> condition) throws Exception;
	
	//글 등록
	public int insertBoard(Board board, MultipartHttpServletRequest mRequest) throws Exception;
	
	//글 수정
	public int updateBoard(Board board, MultipartHttpServletRequest mRequest) throws Exception;
	
	//글 삭제
	public int deleteBoard(Board board) throws Exception;
	
	//갤러리 리스트 
	public ArrayList<Board> selectGalleryList(Map<String, Object> map) throws Exception;
}
