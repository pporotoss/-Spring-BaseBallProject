package com.baseball.member.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baseball.member.model.domain.Member;
import com.baseball.member.model.service.MemberService;

/* 회원관련 JSON처리용. */
@RestController
@RequestMapping("/member")
public class MemberAPIController {

	@Autowired
	private MemberService memberService;
	
	// 회원가입
	@RequestMapping(value="/regist", method=RequestMethod.POST)
	public boolean registMember(@RequestBody Member member){
		
		boolean isSuccess = false;
		
		int result = memberService.registMember(member);
		
		if(result == 1){
			isSuccess = true;
		}
		
		return isSuccess;
	}
	
	// 회원탈퇴
	@RequestMapping(value="/myinfo/{member_id}", method=RequestMethod.DELETE)
	public ResponseEntity<Integer> deleteMember(HttpServletRequest request, @PathVariable("member_id") int member_id){
		
		int result = memberService.deleteMember(request, member_id);
		request.getSession().invalidate();
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	// 로그인하기!!
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login(Member member, String rememberId, HttpSession session, HttpServletResponse response){
		
		String referer = memberService.loginMember(member, rememberId, session, response);	// 로그인 정보 확인.
		
		return referer; // 로그인 이전에 보던 페이지반환.
	}
	
	// 아이디 찾기
	@RequestMapping(value="/searchUserId", method=RequestMethod.GET)
	public String searchUserId(String email){
		
		String result = memberService.searchUserId(email);
		
		return result;
	}
	
	// 비밀번호 찾기
	@RequestMapping(value="/searchUserPwd", method=RequestMethod.GET)
	public String searchUserPwd(String id, String pwdHintAnswer) {
		
		String result = memberService.searchUserPwd(id, pwdHintAnswer);
		
		return result;
	}
	
	// 비밀번호 질문 가져오기
	@RequestMapping(value="/getPwdHintQuestion", method=RequestMethod.GET)
	public String getPwdHintQuestion(String id) {

		String result = memberService.getPwdHintQuestion(id);
		
		return result;
	}
	
	
	// 중복 아이디 체크
	@RequestMapping(value="/chkid/{id}", method=RequestMethod.GET,  produces = "application/json; charset=utf8")
	public ResponseEntity<String> chkId(@PathVariable("id") String id){
		String resultMsg = "{\"msg\":\"사용가능한 아이디 입니다.\",\"result\":false}";
		
		boolean result = memberService.chkId(id);	// 중복아이디 체크
		if(result){
			resultMsg = "{\"msg\":\"동일한 아이디가 존재합니다.\",\"result\":true}";
		}
		
		return new ResponseEntity<String>(resultMsg, HttpStatus.OK);
	}
	
	// 닉네임 체크
	@RequestMapping(value="/chknick/{nickname}", method=RequestMethod.GET,  produces = "application/json; charset=utf8")
	public ResponseEntity<String> chkNick(@PathVariable("nickname") String nickname){
		String resultMsg = "{\"msg\":\"사용가능한 별명 입니다.\",\"result\":false}";
		
		boolean result = memberService.chkNickname(nickname);	// 중복 별명 체크
		if(result){
			resultMsg = "{\"msg\":\"동일한 별명이 존재합니다.\",\"result\":true}";
		}
		
		return new ResponseEntity<String>(resultMsg, HttpStatus.OK);
	}
	
}
