package kr.ac.hit.spring.login.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.ac.hit.spring.member.model.Member;
import kr.ac.hit.spring.member.service.MemberService;

@Controller
public class LoginController {
	@Autowired
	MemberService memberService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	
	@RequestMapping(value = "/login/loginForm")
		public String loginForm() {
		return "login/loginForm";
	}
	@RequestMapping(value = "/login/login")
	public String login(
			@RequestParam(value="memId") String memId,
			@RequestParam(value="memPwd") String memPwd,
			Model model,
			HttpSession session
			) throws Exception {

		// 로그인 처리 이후 세션에 정보 저장
		
		Map<String, Object> paramMap = new HashMap<String, Object>() ;
		paramMap.put("memId", memId);
		
		Member member = memberService.getMember(paramMap);
		
		String message = "";
		boolean isError = false;
		String viewPage = "redirect:/";
		
		if(member != null) {
			// 회원정보가 있는 경우 패스워드 비교(사용자 입력값, db에 암호화 되어 있는 패스워드)
			boolean isPwdCheck = passwordEncoder.matches(memPwd, member.getMemPwd());
			
			if(isPwdCheck) {
				session.setAttribute("LOGIN_USER",member);
				message = member.getMemName() + "환영합니다.";
				isError = false;
			}else {
				// 비밀번호 불일치
				isError = true;
				message = "회원정보가 없습니다. 아이디나 비밀번호를 확인해주세요.";
			}
		
		}else {
			// member 가 null 인경우. 회원정보가 없을 경우
			isError = true;
			message = "회원정보가 없습니다. 아이디나 비밀번호를 확인해주세요.";
		}
		
		model.addAttribute("isError", isError);
		model.addAttribute("message", message);
		
		if(isError) {
			viewPage = "common/message";
		}
		
		
		return viewPage;
	}
	
	@RequestMapping(value = "/login/logout")
	public String logout(HttpSession session) {
		// 세션 정보 삭제
		session.invalidate();
//		session.removeAttribute("LOGIN_USER");
//		session.setAttribute("LOGIN_USER", null);		
		
		return "redirect:/";
	}
	
}
