package kr.ac.hit.spring.board.model;

import java.util.ArrayList;
import java.util.List;

import kr.ac.hit.spring.file.model.FileItem;

public class Board {
	private int boSeqNo;
	private String boType; 			//게시판 타입
	private String boTitle; 		//제목
	private String boContent; 		//내용
	private String boWriter; 		//작성자(ID)
	private String boWriterName; 	//작성자이름
	private int boHitCnt; 			//조회수
	private String boOpenYn; 		//공개여부
	private String boDelYn; 		//삭제여부
	private String regDate; 		//등록일
	private String regUser; 		//등록한 사람
	private String updDate;			//수정일
	private String updUser; 		//수정한 사람
	private String fileSeqNo;
	
	private ArrayList<FileItem> fileList; //getter, setter 메서드 추가 
	
	private String[] delFileSeq;
	
	public int getBoSeqNo() {
		return boSeqNo;
	}
	public void setBoSeqNo(int boSeqNo) {
		this.boSeqNo = boSeqNo;
	}
	public String getBoType() {
		return boType;
	}
	public void setBoType(String boType) {
		this.boType = boType;
	}
	public String getBoTitle() {
		return boTitle;
	}
	public void setBoTitle(String boTitle) {
		this.boTitle = boTitle;
	}
	public String getBoContent() {
		return boContent;
	}
	public void setBoContent(String boContent) {
		this.boContent = boContent;
	}
	public String getBoWriter() {
		return boWriter;
	}
	public void setBoWriter(String boWriter) {
		this.boWriter = boWriter;
	}
	public String getBoWriterName() {
		return boWriterName;
	}
	public void setBoWriterName(String boWriterName) {
		this.boWriterName = boWriterName;
	}
	public int getBoHitCnt() {
		return boHitCnt;
	}
	public void setBoHitCnt(int boHitCnt) {
		this.boHitCnt = boHitCnt;
	}
	public String getBoOpenYn() {
		return boOpenYn;
	}
	public void setBoOpenYn(String boOpenYn) {
		this.boOpenYn = boOpenYn;
	}
	public String getBoDelYn() {
		return boDelYn;
	}
	public void setBoDelYn(String boDelYn) {
		this.boDelYn = boDelYn;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getRegUser() {
		return regUser;
	}
	public void setRegUser(String regUser) {
		this.regUser = regUser;
	}
	public String getUpdDate() {
		return updDate;
	}
	public void setUpdDate(String updDate) {
		this.updDate = updDate;
	}
	public String getUpdUser() {
		return updUser;
	}
	public void setUpdUser(String updUser) {
		this.updUser = updUser;
	}
	public List<FileItem> getFileList() {
		return fileList;
	}
	public void setFileList(ArrayList<FileItem> fileList) {
		this.fileList = fileList;
	}
	public String[] getDelFileSeq() {
		return delFileSeq;
	}
	public void setDelFileSeq(String[] delFileSeq) {
		this.delFileSeq = delFileSeq;
	}
	public String getFileSeqNo() {
		return fileSeqNo;
	}
	public void setFileSeqNo(String fileSeqNo) {
		this.fileSeqNo = fileSeqNo;
	}
	

}
