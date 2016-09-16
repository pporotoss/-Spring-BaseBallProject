package common.intercepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


/* 로그인 안한거 체크하기 */
public class LoginCheckInterceptor extends HandlerInterceptorAdapter{
	
	// 메서드 실행전에 검사하기
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) 	throws Exception {
		
		HttpSession session = request.getSession();
		
		if(session != null){
			Object admin = session.getAttribute("loginMember");
			if(admin != null){	// 세션에 넣은 값이 존재하면,
				return true;	// 리턴값이 true이면 메서드 실행.
			}
		}
		response.sendRedirect("/view/member/loginPage");	// 로그인 페이지로 이동.
		
		return false; // 리턴값이 false이면 메서드 실행 안함.
	}
}
