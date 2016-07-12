package com.baseball.exception;


/* 로그인 오류 */
public class RegistFailException extends RuntimeException{
	
	public RegistFailException(String msg) {
		super(msg);
	}
}
