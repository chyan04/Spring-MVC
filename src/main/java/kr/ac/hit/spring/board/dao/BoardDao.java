package kr.ac.hit.spring.board.dao;

import java.util.ArrayList;
import java.util.Map;

import kr.ac.hit.spring.board.model.Board;

public interface BoardDao {
	
	//<select id="selectBoardCount" parameterType="map" resultType="int">
	public int selectBoardCount(Map<String, Object> map) throws Exception;
	
	// 목록조회, List or ArrayList
	public ArrayList<Board> selectBoardList(Map<String, Object> map) throws Exception;
	
	//한건 조회
	public Board selectBoard(Map<String, Object> condition) throws Exception;
	
	//조회 수 증가
	public int updateHitCnt(Map<String, Object> condition) throws Exception;
	
	//등록
	public int insertBoard(Board board) throws Exception;
	
	//수정
	public int updateBoard(Board board) throws Exception;
	
	//삭제
	public int deleteBoard(Board board) throws Exception;
	
	//갤러리 리스트 
	public ArrayList<Board> selectGalleryList(Map<String, Object> map) throws Exception;
	
}
