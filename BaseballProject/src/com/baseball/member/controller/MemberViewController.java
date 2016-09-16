package com.baseball.member.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.baseball.board.model.domain.Board;
import com.baseball.board.model.domain.Comment;
import com.baseball.exception.LoginFailException;
import com.baseball.member.model.domain.Member;
import com.baseball.member.model.domain.MemberDetail;
import com.baseball.member.model.service.MemberService;
import com.baseball.photoboard.model.domain.PhotoBoard;
import com.baseball.team.model.service.TeamService;

import common.Pager;

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
	
	// 로그인 페이지 이동!!
	@RequestMapping(value="/loginPage", method=RequestMethod.GET)
	public String gotoLogin(Model model, HttpServletRequest request, @CookieValue(value="REMEMBER", required=false) Cookie cookie){
		
		if(cookie != null){	// 저장된 쿠키중에서 REMEMBER라는 쿠키가 있으면, 
			model.addAttribute("rememberId", cookie.getValue());
		}
		String referer = request.getHeader("Referer"); // 헤더의 referer 이용해서 이전에 보던 페이지 주소 얻어오기!!
		
		if(referer != null){
			String[] refererArray = referer.split("/");
			if(refererArray.length <= 3){	// 메인페이지면,
				referer = "/";
			}else{
				StringBuffer refererBuffer = new StringBuffer();
				
				for(int i = 3; i < refererArray.length; i++){
					refererBuffer.append("/");
					refererBuffer.append(refererArray[i]);
				}
				referer = refererBuffer.toString();
			}
		}else{
			referer = "/";
		}
		
		HttpSession session = request.getSession();	
		session.setAttribute("referer", referer); // 이전에 보던 페이지주소 세션에 담기!!
		
		return "member/login";
	}
	
	// 아이디 비번 찾기 페이지 이동
	@RequestMapping(value="/find_user", method=RequestMethod.GET)
	public String find_user(){
		
		return "member/find_user";
	}
	
	
	// 로그아웃!!
	@RequestMapping("/logout")
	public String logout(HttpSession session){
		
		session.invalidate();
		
		return "redirect:/";	// 메인페이지로 이동.
	}
	
	/* 회원정보 */
	// 회원정보 이동
	@RequestMapping(value="/myinfo/{member_id}", method=RequestMethod.GET) 
	public String myPage(Model model, @PathVariable("member_id") int member_id){
		
		MemberDetail memberDetail = memberService.selectMember(member_id);
		
		model.addAttribute("memberDetail", memberDetail);
		List teamList = teamService.selectList();
		model.addAttribute("teamList", teamList);
		
		return "member/myinfo";
	}
	
	// 회원정보 수정하기
	@RequestMapping(value="/myinfo/{member_id}", method=RequestMethod.PUT)
	public String updateInfo(Model model, @PathVariable("member_id") int member_id, Member member){
		
		member.setMember_id(member_id);
		memberService.updateMember(member);
		
		return "redirect:/view/member/myinfo/"+member.getMember_id();
	}
	
	
	// 회원 탈퇴
	@RequestMapping(value="/myinfo/{member_id}", method=RequestMethod.DELETE)
	public String deleteMember(HttpServletRequest request, @PathVariable("member_id") int member_id){
		
		memberService.deleteMember(request, member_id);
		request.getSession().invalidate();
		
		return "redirect:/";
	}
	
	/*  활동내역  */

	// 자유게시판 작성글
	@RequestMapping(value="/activityList/{member_id}/freeBoard", method=RequestMethod.GET)
	public String freeBoardList(Model model, @PathVariable("member_id") int member_id, String page){
		
		Map map = memberService.freeBoardList(member_id, page);
		List<Board> freeBoardList = (List)map.get("freeBoardList");
		Pager pager = (Pager) map.get("pager");
		
		model.addAttribute("freeBoardList", freeBoardList);
		model.addAttribute("pager", pager);
	
		return "member/activityList";
	}
	
	// 자유게시판 댓글
	@RequestMapping(value="/activityList/{member_id}/freeComment", method=RequestMethod.GET)
	public String freeCommentList(Model model, @PathVariable("member_id") int member_id, String page){
		
		Map map = memberService.freeCommentList(member_id, page);
		List<Comment> freeCommentList = (List)map.get("freeCommentList");
		Pager pager = (Pager) map.get("pager");
		
		model.addAttribute("freeCommentList", freeCommentList);
		model.addAttribute("pager", pager);
		
		return "member/activityList";
	}
	
	// 사진게시판 작성글
		@RequestMapping(value="/activityList/{member_id}/photoBoard", method=RequestMethod.GET)
		public String photoBoardList(Model model, @PathVariable("member_id") int member_id, String page){
			
			Map map = memberService.photoBoardList(member_id, page);
			List<PhotoBoard> photoBoardList = (List)map.get("photoBoardList");
			Pager pager = (Pager) map.get("pager");
			
			model.addAttribute("photoBoardList", photoBoardList);
			model.addAttribute("pager", pager);
		
			return "member/activityList";
		}
		
		// 사진게시판 댓글
		@RequestMapping(value="/activityList/{member_id}/photoComment", method=RequestMethod.GET)
		public String photoCommentList(Model model, @PathVariable("member_id") int member_id, String page){
			
			Map map = memberService.photoCommentList(member_id, page);
			List<Comment> photoCommentList = (List)map.get("photoCommentList");
			Pager pager = (Pager) map.get("pager");
			
			model.addAttribute("photoCommentList", photoCommentList);
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
