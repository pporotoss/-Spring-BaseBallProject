package com.baseball.member.model.service;

import java.util.List;
import java.util.Map;

import com.baseball.member.model.domain.Member;

public interface MemberService {
	
	public List memberList();
	public Map memberAll(String page, String keyword);
	public Map memberRank(String page, String rank);
	public List LevelAll();
	public Member selectMember(int member_id);
	public void registMember(Member member);
	public void updateMember(Member member);
	public void deleteMember(int[] member_id);
	public void deleteMember(int member_id);
	public Member loginMember(Member member);
	public boolean chkId(String id);
	public boolean chkNickname(String nickname);
	public void updateMemberLevel(int[] member_id, int level_id);
	
}
