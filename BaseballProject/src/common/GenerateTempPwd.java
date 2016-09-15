package common;

import java.util.Random;

/* �빮��, �ҹ���, ���ڷ� �̷���� �ӽ� ��й�ȣ ������ */
public class GenerateTempPwd {
	
	public static String generatePwd(int pwdLength){	// �Է¹��� ���̸�ŭ ����.
		
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		
		for(int i = 0; i < pwdLength; i++){
			int ran = random.nextInt(3)+1;	//	1~3���� ���� ���� ����.
			if(ran == 1){
				sb.append(random.nextInt(10));	// ����
			}else if(ran == 2){
				sb.append((char)((Math.random() * 26)+65));	// �빮��
			}else{
				sb.append((char)((Math.random() * 26)+97));	// �ҹ���
			}
		}// i
		
		return sb.toString();
	}
	
}
