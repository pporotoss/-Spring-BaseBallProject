package common.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice	// ��Ʈ�ѷ����� �߻��ϴ� ��� ���ܸ� ó��. �� ��� ����� �۵�.
public class CommonExceptionHandler {
	
	@ExceptionHandler(RuntimeException.class)	// ��Ÿ�� ���� �߻��� �޼��� ����.
	public String handleRuntimeException() {
		
		return "redirect:/error.html";
	}
	
}
