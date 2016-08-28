package com.baseball.member.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.baseball.exception.LoginFailException;
import com.baseball.member.model.domain.Member;
import com.baseball.member.model.domain.MemberDetail;
import com.baseball.member.model.service.MemberService;
import com.baseball.team.model.service.TeamService;

import common.Pager;

@Controller
@RequestMapping("/member")
public class MemberViewController {
	
	@Autowired
	private TeamService teamService;
	
	@Autowired
	private MemberService memberService;
	
	// ȸ������ �������� �̵�!!
	@RequestMapping(value="/regist", method=RequestMethod.GET)
	public String memberRegist(Model model, HttpServletRequest request){
		
		List teamList = teamService.selectList();
		model.addAttribute("teamList", teamList);
		
		return "member/regist";
	}
	
	// ȸ�� �����ϱ�!!
	@RequestMapping(value="/regist", method=RequestMethod.POST)
	public String memberInsert(Member member){
		
		memberService.registMember(member);
		
		return "redirect:/"; // ������������ �̵�!!
	}
	
	// �α��� ������ �̵�!!
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String gotoLogin(HttpServletRequest request){
		
		String referer = request.getHeader("Referer"); // ����� referer �̿��ؼ� ������ ���� ������ �ּ� ������!!
		HttpSession session = request.getSession();	// ������ ���� ������ ���ǿ� ���!!
		session.setAttribute("referer", referer.substring(referer.lastIndexOf(":")+5));
				
		return "member/login";
	}
	
	// �α����ϱ�!!
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(Member member, HttpSession session){
		
		Member loginMember = memberService.loginMember(member);
		session.setAttribute("loginMember", loginMember); // �α����� ��� ���� ���ǿ� ���!!
		
		String referer = (String)session.getAttribute("referer");	 // ���ǿ� ��Ƴ��� �α��� ������ ���� ������ �ּ� ������!!
		session.removeAttribute("referer"); // ���ǿ��� �������ּ� ����!!
		
		return "redirect:"+referer; // �α��� ������ ���� �������� �̵�!!
	}
	
	// �α׾ƿ�!!
	@RequestMapping("/logout")
	public String logout(HttpSession session){
		
		session.invalidate();
		
		return "redirect:/";	// ������������ �̵�.
	}
	
	// ���������� �̵�
	@RequestMapping(value="/myinfo/{member_id}", method=RequestMethod.GET) 
	public String myPage(Model model, @PathVariable("member_id") int member_id){
		
		MemberDetail memberDetail = memberService.selectMember(member_id);
		
		model.addAttribute("memberDetail", memberDetail);
		List teamList = teamService.selectList();
		model.addAttribute("teamList", teamList);
		
		return "member/myinfo";
	}
	
	// ȸ������ �����ϱ�
	@RequestMapping(value="/myinfo", method=RequestMethod.PUT)
	public String updateInfo(Model model, Member member){
		
		memberService.updateMember(member);
		
		return "redirect:/view/member/myinfo/"+member.getMember_id();
	}
	
	
	// ȸ�� Ż��
	public String deleteMember(){
		
		return "";
	}
	
	/*  Ȱ������  */

	// �����Խ���
	@RequestMapping(value="/activityList/{member_id}", method=RequestMethod.GET)
	public String activityList(Model model, @PathVariable("member_id") int member_id, String page){
		
		Map map = memberService.freeBoardList(member_id, page);
		List<MemberDetail> freeBoardList = (List)map.get("freeBoardList");
		Pager pager = (Pager) map.get("pager");
		
		model.addAttribute("freeBoardList", freeBoardList);
		model.addAttribute("pager", pager);
	
		return "member/activityList";
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
