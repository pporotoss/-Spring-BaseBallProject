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
		String resultMsg = "{\"msg\":\"사용가능한 아이디 입니다.\",\"result\":false}";
		
		boolean result = memberService.chkId(id);	// 중복아이디 체크
		if(result){
			resultMsg = "{\"msg\":\"동일한 아이디가 존재합니다.\",\"result\":true}";
		}
		
		return new ResponseEntity<String>(resultMsg, HttpStatus.OK);
	}
	
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
