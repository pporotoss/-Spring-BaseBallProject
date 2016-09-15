package com.baseball.member.model.repository;

import java.util.List;
import java.util.Map;

import com.baseball.member.model.domain.Member;
import com.baseball.member.model.domain.MemberDetail;

public interface MemberDAO {
	
	public List selectList();	// ����� �ҷ�����
	public List memberAll(Map searchMap);	// ��� ���� �������� �ҷ�����
	public List memberSearch(Map searchMap);	// ��� �˻��ϱ�
	public List memberLevel(Map levelMap);	// ��ũ���� �ҷ�����
	public int totalMember();	// �� ����� �ҷ�����.
	public int totalMember(Map searchKeyword);	// �˻� ����� �ҷ�����.
	public MemberDetail selectMember(int member_id);
	public int insertMember(Member member);
	public int updateMember(Member member);
	public int deleteMember(int member_id);
	public Member loginMember(String id);
	public List getId();
	public List getNickname();
	public int updateMemberLevel(Member member);
	public String searchUserId(String email);
	public int searchUserPwd(Map user);
	public String getPwdHintQuestion(String id);
	public int saveTempPwd(Map pwdParam);
	
}
