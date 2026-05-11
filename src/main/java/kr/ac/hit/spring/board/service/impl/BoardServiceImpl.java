package kr.ac.hit.spring.board.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.ac.hit.spring.board.dao.BoardDao;
import kr.ac.hit.spring.board.model.Board;
import kr.ac.hit.spring.board.service.BoardService;
import kr.ac.hit.spring.common.util.FileUtils;
import kr.ac.hit.spring.file.dao.FileItemDao;
import kr.ac.hit.spring.file.model.FileItem;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	BoardDao boardDao;
	
	@Autowired
	FileItemDao fileItemDao;
	
	@Autowired
	FileUtils fileUtils;

	@Override
	public int selectBoardCount(Map<String, Object> map) throws Exception {
		return boardDao.selectBoardCount(map);
	}

	@Override
	public ArrayList<Board> selectBoardList(Map<String, Object> map) throws Exception {
		return boardDao.selectBoardList(map);
	}

	@Transactional(isolation = Isolation.READ_COMMITTED)
	@Override 
	public Board selectBoard(Map<String, Object> condition) throws Exception {
		//게시글 조회 수 증가
		boardDao.updateHitCnt(condition);
				
		//게시글 상세 조회(1건 조회)
		Board board = boardDao.selectBoard(condition);
		
		//파일목록 조회
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("refSeqNo", board.getBoSeqNo()); //첨부파일 조회를 위해 게시글 번호 지정
		paramMap.put("bizType", board.getBoType()); //첨부파일 조회를 위해 게시판 타입 지정
		
		ArrayList<FileItem> fileList = fileItemDao.selectFileItemList(paramMap); //첨부파일 목록 조회
		board.setFileList(fileList); //첨부파일 목록을 board객체에 저장
		
		return board;
	}

	@Transactional
	@Override
	public int insertBoard(Board board, MultipartHttpServletRequest mRequest) throws Exception {
		//게시글 저장 및 파일정보 저장
		int updCnt = boardDao.insertBoard(board);
		
		//boSeqNo를 넘겨주기 위해서 board객체를 같이 전달
		List<FileItem> fileList = fileUtils.uploadFiles(board, mRequest);
		for(FileItem fileItem : fileList) {
			fileItemDao.insertFileItem(fileItem);			
		}
		
		return updCnt;
	}

	@Transactional
	@Override
	public int updateBoard(Board board, MultipartHttpServletRequest mRequest) throws Exception {
		String[] delFileSeq = board.getDelFileSeq();
		
		try {
			//기존 첨부파일을 삭제하는 경우
			if(delFileSeq != null) {
				for(int i = 0; i<delFileSeq.length; i++) {
					HashMap<String, Object> condition = new HashMap<String, Object>();
					condition.put("delFileSeq", delFileSeq[i]);
					
					fileItemDao.deleteFileItem(condition);
				}				
			}
			 
			//새로 추가된 파일이 있는 경우
			List<FileItem> fileList = fileUtils.uploadFiles(board, mRequest);
			for(FileItem fileItem : fileList) {
				fileItemDao.insertFileItem(fileItem);			
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return boardDao.updateBoard(board);
	}

	@Override
	public int deleteBoard(Board board) throws Exception {

		return boardDao.deleteBoard(board);
	}

	@Override
	public ArrayList<Board> selectGalleryList(Map<String, Object> map) throws Exception {

		return boardDao.selectGalleryList(map);
	}
}
