package com.baseball.member.model.service;

import java.util.List;

import javax.security.auth.login.LoginException;

import com.baseball.member.model.domain.Member;

public interface MemberService {
	
	public List memberList();
	public List memberAll();
	public List LevelAll();
	public Member selectMember(int member_id);
	public void registMember(Member member);
	public void updateMember(Member member);
	public void deleteMember(int[] member_id);
	public Member loginMember(Member member);
	public boolean chkId(String id);
	public boolean chkNickname(String nickname);
	public void updateMemberLevel(int[] member_id, int level_id);
	
}
