package kr.ac.hit.spring.member.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kr.ac.hit.spring.member.model.Member;

public interface MemberService {

	public ArrayList<Member> selectMemberTest() throws Exception;
	
	public List<Member> getMemberList(Map<String, Object> paramMap) throws Exception;

	public int getMemberCount(Map<String, Object> paramMap) throws Exception;
	
	public Member getMember(Map<String, Object> paramMap) throws Exception;
	
	public int insertMember(Member member) throws Exception;
	
	public int updateMember(Member member) throws Exception;

	public int deleteMember(Member member) throws Exception;
	
}