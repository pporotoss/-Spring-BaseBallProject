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

/* ȸ������ JSONó����. */
@RestController
@RequestMapping("/member")
public class MemberAPIController {

	@Autowired
	private MemberService memberService;
	
	// ȸ������
	@RequestMapping(value="/regist", method=RequestMethod.POST)
	public boolean registMember(@RequestBody Member member){
		
		boolean isSuccess = false;
		
		int result = memberService.registMember(member);
		
		if(result == 1){
			isSuccess = true;
		}
		
		return isSuccess;
	}
	
	// ȸ��Ż��
	@RequestMapping(value="/myinfo/{member_id}", method=RequestMethod.DELETE)
	public ResponseEntity<Integer> deleteMember(HttpServletRequest request, @PathVariable("member_id") int member_id){
		
		int result = memberService.deleteMember(request, member_id);
		request.getSession().invalidate();
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	// �α����ϱ�!!
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login(Member member, String rememberId, HttpSession session, HttpServletResponse response){
		
		String referer = memberService.loginMember(member, rememberId, session, response);	// �α��� ���� Ȯ��.
		
		return referer; // �α��� ������ ���� ��������ȯ.
	}
	
	// ���̵� ã��
	@RequestMapping(value="/searchUserId", method=RequestMethod.GET)
	public String searchUserId(String email){
		
		String result = memberService.searchUserId(email);
		
		return result;
	}
	
	// ��й�ȣ ã��
	@RequestMapping(value="/searchUserPwd", method=RequestMethod.GET)
	public String searchUserPwd(String id, String pwdHintAnswer) {
		
		String result = memberService.searchUserPwd(id, pwdHintAnswer);
		
		return result;
	}
	
	// ��й�ȣ ���� ��������
	@RequestMapping(value="/getPwdHintQuestion", method=RequestMethod.GET)
	public String getPwdHintQuestion(String id) {

		String result = memberService.getPwdHintQuestion(id);
		
		return result;
	}
	
	
	// �ߺ� ���̵� üũ
	@RequestMapping(value="/chkid/{id}", method=RequestMethod.GET,  produces = "application/json; charset=utf8")
	public ResponseEntity<String> chkId(@PathVariable("id") String id){
		String resultMsg = "{\"msg\":\"��밡���� ���̵� �Դϴ�.\",\"result\":false}";
		
		boolean result = memberService.chkId(id);	// �ߺ����̵� üũ
		if(result){
			resultMsg = "{\"msg\":\"������ ���̵� �����մϴ�.\",\"result\":true}";
		}
		
		return new ResponseEntity<String>(resultMsg, HttpStatus.OK);
	}
	
	// �г��� üũ
	@RequestMapping(value="/chknick/{nickname}", method=RequestMethod.GET,  produces = "application/json; charset=utf8")
	public ResponseEntity<String> chkNick(@PathVariable("nickname") String nickname){
		String resultMsg = "{\"msg\":\"��밡���� ���� �Դϴ�.\",\"result\":false}";
		
		boolean result = memberService.chkNickname(nickname);	// �ߺ� ���� üũ
		if(result){
			resultMsg = "{\"msg\":\"������ ������ �����մϴ�.\",\"result\":true}";
		}
		
		return new ResponseEntity<String>(resultMsg, HttpStatus.OK);
	}
	
}
