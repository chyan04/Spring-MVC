package kr.ac.hit.spring.member.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.ac.hit.spring.common.util.PagingUtil;
import kr.ac.hit.spring.member.model.Member;
import kr.ac.hit.spring.member.service.MemberService;

@Controller
@RequestMapping(value = "/member")
public class MemberController {
	@Autowired
	private MemberService memberService;
	
	@Inject
	PasswordEncoder passwordEncoder;

	// memberTest
	@RequestMapping(value = "/memberTest")
	public String memberTest(Model model) throws Exception {

		ArrayList<Member> memberList = memberService.selectMemberTest();
		model.addAttribute("memberList", memberList);

		return "/member/memberTest"; 		// view(j s p)의 경로
	}

	@RequestMapping(value = "/memberList")
	public String memberList(@RequestParam(value = "searchType", required = false, defaultValue = "") String searchType,
			@RequestParam(value = "searchWord", required = false, defaultValue = "") String searchWord,
			@RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage,
			@RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize, Model model)
			throws Exception {

		int pageCount = 5;
		int totalCount = 0;

		List<Member> memberList = null;
		// ArrayList

		Map<String, Object> paramMap = new HashMap<String, Object>();

		if (!StringUtils.isBlank(searchWord) && !StringUtils.isBlank(searchType)) {
			paramMap.put("searchWord", searchWord);
			paramMap.put("searchType", searchType);
		}

		// 게시물 총 갯수 구하기
		totalCount = memberService.getMemberCount(paramMap);

		PagingUtil pagingUtil = new PagingUtil(currentPage, totalCount, pageSize, pageCount);

		paramMap.put("startRow", pagingUtil.getStartRow());
		paramMap.put("endRow", pagingUtil.getEndRow());
		System.out.println("startRow : " + pagingUtil.getStartRow());
		System.out.println("endRow : " + pagingUtil.getEndRow());

		memberList = memberService.getMemberList(paramMap);

		model.addAttribute("memberList", memberList); // request.setAttribute("memberList", memberList);
		model.addAttribute("pagingUtil", pagingUtil);

		System.out.println("memberList :" + memberList.size());

		return "member/memberList";
	}
	@RequestMapping(value = "/memberView") // member/memberView
	public String memberView(@RequestParam(value = "seqNo", required = true) int seqNo, Model model) throws Exception {
		// 회원정보 한 건 조회
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("seqNo", seqNo); // s q l m a p p e r 에서 사용시 #{seqNp}

		Member member = memberService.getMember(paramMap);
		model.addAttribute("member", member);

		return "member/memberView"; // j s p 경로
	}

	@RequestMapping(value = "/memberForm")
	public String memberForm(@RequestParam(value = "seqNo", required = false, defaultValue = "0") int seqNo,
			Model model) throws Exception {
		Member member = new Member();

		if (seqNo != 0) {
			// seqNo가 있으면 수정, 없으면 가입화면
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("seqNo", seqNo);
			member = memberService.getMember(map);
		}

		model.addAttribute("member", member);

		return "member/memberForm";
	}

	@RequestMapping(value = "/memberInsert", method = RequestMethod.POST)
	public String memberInsert(Member member, Model model) throws Exception {

		boolean isError = false;

		try {
			
			String enPwd = passwordEncoder.encode(member.getMemPwd());
			member.setMemPwd(enPwd);

			int updCnt = memberService.insertMember(member);

			if (updCnt == 0) {
				isError = true;
			}

		} catch (Exception e) {
			isError = true;
			e.printStackTrace();
		}

//		String viewPage = "redirect:/member/memberList";

		String viewPage = "common/message";
		String message = "회원 가입이 완료되었습니다.";
		String locationURL = "/member/memberList";

		if (isError) {
			message = "회원 가입에 실패했습니다.";
			viewPage = "common/message";

//			model.addAttribute("message", message);
//			model.addAttribute("isError", isError);
		} else {
			model.addAttribute("locationURL", locationURL);
		}
		model.addAttribute("message", message);
		model.addAttribute("isError", isError);

		return viewPage;
	}

	// 회원정보 조회 (중복체크)
	@RequestMapping(value = "/memberExists", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> memberExists(@RequestParam(value = "memId", required = true) String memId)
			throws Exception {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("memId", memId);

		Member member = memberService.getMember(paramMap);

		Map<String, Object> resulMap = new HashMap<String, Object>();
		if (member != null) {
			resulMap.put("result", "true");
		} else {
			resulMap.put("result", "false");
		}
		return resulMap;
	}

	@RequestMapping(value = "/memberUpdate")
	public String memberUpdate(Member member, Model model) throws Exception {

		boolean isError = false;

		try {
			
			String enPwd = passwordEncoder.encode(member.getMemPwd());
			member.setMemPwd(enPwd);
			
			int updCnt = memberService.updateMember(member);

			if (updCnt == 0) {
				isError = true;
			}
		} catch (Exception e) {
			isError = true;
			e.printStackTrace();
		}

		// String viewPage = "redirect:/member/memberView?seqNo=" +
		// member.getMemSeqNo();
		String viewPage = "common/message";
		String message = "회원 수정이 완료되었습니다.";
		String locationURL = "/member/memberView?seqNo=" + member.getMemSeqNo();

		if (isError) {
			message = "회원 수정에 실패했습니다.";
			viewPage = "common/message";
		} else {
			model.addAttribute("locationURL", locationURL);
		}
		model.addAttribute("message", message);
		model.addAttribute("isError", isError);
		
		return viewPage;
	}

	@RequestMapping(value = "/memberDelete")
	public String memberDelete(Member member, Model model) throws Exception {

		boolean isError = false;

		try {
			int updCnt = memberService.deleteMember(member);

			if (updCnt == 0) {
				isError = true;
			}
		} catch (Exception e) {
			isError = true;
			e.printStackTrace();
		}

		String viewPage = "common/message";
		String message = "회원 삭제가 완료되었습니다.";
		String locationURL = "/member/memberList";

		if (isError) {
			message = "회원 삭제에 실패했습니다.";
			viewPage = "common/message";
		} else {
			model.addAttribute("locationURL", locationURL);
		}
		model.addAttribute("message", message);
		model.addAttribute("isError", isError);
		
		return viewPage;
	}
	
}
