package kr.ac.hit.spring.file.model;

import lombok.Data;

@Data
public class FileItem {
	private int fileSeqNo;
	private int refSeqNo;
	private String bizType;
	private String filePath;
	private String fileName;
	private String fileSaveName;
	private long fileSize;
	private String fileFancySize;
	private int fileDownCnt;
	private String regDate;
	private String regUser;
	private String upoDate;
	private String updUser;
	private String thumSaveName;
} 