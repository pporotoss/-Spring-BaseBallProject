package com.baseball.exception;


/* 로그인 오류 */
public class LoginFailException extends RuntimeException{
	
	public LoginFailException(String msg) {
		super(msg);
	}
}
