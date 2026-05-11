package kr.ac.hit.spring.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginCheckInterceptor extends HandlerInterceptorAdapter{
	
	// controller 실행 전에 호출
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		HttpSession session = request.getSession();
		if(session == null) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN); // 403 Error
			return false; // 컨트롤러 요청 취소
		}
		if(session.getAttribute("LOGIN_USER") == null) {
			// 로그인 하지 않은 상태, 로그인 화면으로 이동
			response.sendRedirect(request.getContextPath() + "/login/loginForm");
			return false; 
		}
		
		return true;
	}
	
	// 컨트롤러 실행 된 후 호출
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		super.postHandle(request, response, handler, modelAndView);
	}
	
	// view 처리 끝난 후 호출
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception modelAndView) throws Exception {
		
		super.afterCompletion(request, response, handler, modelAndView);
	}

}
