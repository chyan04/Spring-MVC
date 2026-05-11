package kr.ac.hit.spring.member.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kr.ac.hit.spring.member.model.Member;

public interface MemberDao {
	
	public ArrayList<Member> selectMemberTest() throws Exception;
	
	// 회원 목록 조회
	public List<Member> selectMemberList(Map<String, Object> paramMap) throws Exception;
	
	// 회원 전체 카운트
	public int selectMemberCount(Map<String, Object> paramMap) throws Exception;
		
	// 회원 정보 한건 조회 
	public Member selectMember(Map<String, Object> paramMap) throws Exception;
	
	// 회원 정보 입력
	public int insertMember(Member member) throws Exception;
	
	// 회원 정보 수정
	public int updateMember(Member member) throws Exception;

	// 회원 삭제
	public int deleteMember(Member member) throws Exception;
	
}