package com.baseball.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baseball.member.model.service.MemberService;

@RestController
@RequestMapping("/member")
public class MemberAPIController {

	@Autowired
	private MemberService memberService;
	
	
	@RequestMapping(value="/chkid/{id}", method=RequestMethod.GET,  produces = "application/json; charset=utf8")
	public ResponseEntity<String> chkId(@PathVariable("id") String id){
		String resultMsg = "{\"msg\":\"��밡���� ���̵� �Դϴ�.\",\"result\":false}";
		
		boolean result = memberService.chkId(id);	// �ߺ����̵� üũ
		if(result){
			resultMsg = "{\"msg\":\"������ ���̵� �����մϴ�.\",\"result\":true}";
		}
		
		return new ResponseEntity<String>(resultMsg, HttpStatus.OK);
	}
	
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
