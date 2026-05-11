package kr.ac.hit.spring.member.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.ac.hit.spring.member.dao.MemberDao;
import kr.ac.hit.spring.member.model.Member;
import kr.ac.hit.spring.member.service.MemberService;

@Service
public class MemberServiceImpl implements MemberService {
	@Autowired
	MemberDao memberDao;

	@Override
	public ArrayList<Member> selectMemberTest() throws Exception {		
		return memberDao.selectMemberTest();
	}

	@Override
	public List<Member> getMemberList(Map<String, Object> paramMap) throws Exception {
		return memberDao.selectMemberList(paramMap);
	}

	@Override
	public int getMemberCount(Map<String, Object> paramMap) throws Exception {
		return memberDao.selectMemberCount(paramMap);
	}
	
	@Override
	public Member getMember(Map<String, Object> paramMap) throws Exception {
		return memberDao.selectMember(paramMap);
	}
	
	@RequestMapping(value="/memberList")
	public String memberList() throws Exception{
		return "member/memberList";
	}

	@Override
	public int insertMember(Member member) throws Exception {
		return memberDao.insertMember(member);
	}

	@Override
	public int updateMember(Member member) throws Exception {
		return memberDao.updateMember(member);
	}

	@Override
	public int deleteMember(Member member) throws Exception {
		return memberDao.deleteMember(member);
	}

}