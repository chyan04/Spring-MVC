package kr.ac.hit.spring.notice.model;

import lombok.Data;

@Data
public class Notice {
		private int noticeSeqNo;
		private String noticeTitle;
		private String noticeContent;
		private String noticeWriter;
		private String noticeWriterName;
		private int noticeHitCnt;
		private String noticeDelYn;
		private String noticeDate;
		private String noticeUpDate;
		private String noticeUpUser;
} 