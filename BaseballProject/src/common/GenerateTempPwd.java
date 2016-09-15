package common;

import java.util.Random;

/* 대문자, 소문자, 숫자로 이루어진 임시 비밀번호 생성기 */
public class GenerateTempPwd {
	
	public static String generatePwd(int pwdLength){	// 입력받은 길이만큼 생성.
		
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		
		for(int i = 0; i < pwdLength; i++){
			int ran = random.nextInt(3)+1;	//	1~3까지 숫자 랜덤 생성.
			if(ran == 1){
				sb.append(random.nextInt(10));	// 숫자
			}else if(ran == 2){
				sb.append((char)((Math.random() * 26)+65));	// 대문자
			}else{
				sb.append((char)((Math.random() * 26)+97));	// 소문자
			}
		}// i
		
		return sb.toString();
	}
	
}
