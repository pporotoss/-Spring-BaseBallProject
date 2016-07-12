package com.baseball.member.controller;

import java.util.List;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.baseball.exception.LoginFailException;
import com.baseball.member.model.domain.Member;
import com.baseball.member.model.service.MemberService;
import com.baseball.team.model.service.TeamService;

@Controller
@RequestMapping("/member")
public class MemberViewController {
	
	@Autowired
	private TeamService teamService;
	
	@Autowired
	private MemberService memberService;
	
	// 회원가입 페이지로 이동!!
	@RequestMapping(value="/regist", method=RequestMethod.GET)
	public String memberRegist(Model model, HttpServletRequest request){
		
		List teamList = teamService.selectList();
		model.addAttribute("teamList", teamList);
		
		return "member/regist";
	}
	
	// 회원 가입하기!!
	@RequestMapping(value="/regist", method=RequestMethod.POST)
	public String memberInsert(Member member){
		
		memberService.registMember(member);
		
		return "redirect:/"; // 메인페이지로 이동!!
	}
	
	// 로그인 페이지 이동!!
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String gotoLogin(HttpServletRequest request){
		
		String referer = request.getHeader("Referer"); // 이전에 보던 페이지 주소 얻어오기!!
		HttpSession session = request.getSession();	// 이전에 보던 페이지 세션에 담기!!
		session.setAttribute("referer", referer.substring(referer.lastIndexOf(":")+5));
				
		return "member/login";
	}
	
	// 로그인하기!!
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(Member member, HttpSession session){
		
		Member loginMember = memberService.loginMember(member);
		session.setAttribute("loginMember", loginMember); // 로그인한 멤버 정보 세션에 담기!!
		
		String referer = (String)session.getAttribute("referer");	 // 로그인 이전에 보던 페이지 주소 얻어오기!!
		session.removeAttribute("referer"); // 세션에서 페이지주소 제거!!
		
		return "redirect:"+referer; // 로그인 이전에 보던 페이지로 이동!!
	}
	
	// 로그아웃!!
	@RequestMapping("/logout")
	public String logout(HttpSession session){
		
		session.invalidate();
		
		return "redirect:/";	// 메인페이지로 이동.
	}
	
	// 회원 삭제
	public String deleteMember(){
		
		return "";
	}
	
	@ExceptionHandler(LoginFailException.class)
	public ModelAndView loginFail(LoginFailException e){
		
		ModelAndView mav = new ModelAndView();
		
		String msg = e.getMessage();
		mav.addObject("msg", msg);
		mav.setViewName("member/login");
		
		return mav;
	}
	
}
