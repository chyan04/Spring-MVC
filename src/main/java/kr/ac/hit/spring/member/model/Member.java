package kr.ac.hit.spring.member.model;

public class Member {
	private int memSeqNo;
	private String memId;
	private String memName;
	private String memPwd;
	private String memBirth;
	private String memPhone;
	private String memEmail;
	private String memZipcode;
	private String memAddrMaster;
	private String memAddrDetail;
	private String memType;
	public String getMemId() {
		return memId;
	}
	public void setMemId(String memId) {
		this.memId = memId;
	}
	public String getMemName() {
		return memName;
	}
	public void setMemName(String memName) {
		this.memName = memName;
	}
	public String getMemPwd() {
		return memPwd;
	}
	public void setMemPwd(String memPwd) {
		this.memPwd = memPwd;
	}
	public String getMemBirth() {
		return memBirth;
	}
	public void setMemBirth(String memBirth) {
		this.memBirth = memBirth;
	}
	public String getMemPhone() {
		return memPhone;
	}
	public void setMemPhone(String memPhone) {
		this.memPhone = memPhone;
	}
	public String getMemEmail() {
		return memEmail;
	}
	public void setMemEmail(String memEmail) {
		this.memEmail = memEmail;
	}
	public String getMemZipcode() {
		return memZipcode;
	}
	public void setMemZipcode(String memZipcode) {
		this.memZipcode = memZipcode;
	}
	public String getMemAddrMaster() {
		return memAddrMaster;
	}
	public void setMemAddrMaster(String memAddrMaster) {
		this.memAddrMaster = memAddrMaster;
	}
	public String getMemAddrDetail() {
		return memAddrDetail;
	}
	public void setMemAddrDetail(String memAddrDetail) {
		this.memAddrDetail = memAddrDetail;
	}
	public String getMemType() {
		return memType;
	}
	public void setMemType(String memType) {
		this.memType = memType;
	}
	public int getMemSeqNo() {
		return memSeqNo;
	}
	public void setMemSeqNo(int memSeqNo) {
		this.memSeqNo = memSeqNo;
	}
}
