package common.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice	// 컨트롤러에서 발생하는 모든 예외를 처리. 빈 등록 해줘야 작동.
public class CommonExceptionHandler {
	
	@ExceptionHandler(RuntimeException.class)	// 런타임 예외 발생시 메서드 실행.
	public String handleRuntimeException() {
		
		return "redirect:/error.html";
	}
	
}
