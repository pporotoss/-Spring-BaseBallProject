package com.baseball.member.model.repository;

import java.util.List;
import java.util.Map;

import com.baseball.member.model.domain.Member;

public interface MemberDAO {
	
	public List selectList();	// ����� �ҷ�����
	public List memberAll(Map searchMap);	// ��� ���� �������� �ҷ�����
	public List memberSearch(Map searchMap);	// ��� �˻��ϱ�
	public List memberLevel(Map levelMap);	// ��ũ���� �ҷ�����
	public int totalMember();	// �� ����� �ҷ�����.
	public Member selectMember(int member_id);
	public int insertMember(Member member);
	public int updateMember(Member member);
	public int deleteMember(int member_id);
	public Member loginMember(Member member);
	public List getId();
	public List getNickname();
	public int updateMemberLevel(Member member);
}
