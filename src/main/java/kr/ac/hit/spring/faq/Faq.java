package kr.ac.hit.spring.faq;

import lombok.Data;

@Data
public class Faq {
	private int faqSeqNo;
	private String faqName;
	private String faqContent;
	private int faqHitCnt;
	private String faqDate;
	private String faqWriter;
	private String faqWriterName;
	private String faqUpDate;
	private String faqUpUser;
	private String faqDelYn;
	private String faqDelDate;
	private String faqDelUser;
}
