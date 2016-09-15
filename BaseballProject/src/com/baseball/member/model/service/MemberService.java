package com.baseball.member.model.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.baseball.member.model.domain.Member;
import com.baseball.member.model.domain.MemberDetail;

public interface MemberService {
	
	public List memberList();
	public Map memberAll(String page, String keyword);
	public Map memberLevel(String page, String level_id);
	public List LevelAll();
	public MemberDetail selectMember(int member_id);
	public int registMember(Member member);
	public int updateMember(Member member);
	public boolean deleteMember(HttpServletRequest request, int[] member_id);
	public int deleteMember(HttpServletRequest request, int member_id);
	public String loginMember(Member member, String rememberId, HttpSession session, HttpServletResponse response);
	public boolean chkId(String id);
	public boolean chkNickname(String nickname);
	public void updateMemberLevel(int[] member_id, int level_id);
	public Map freeBoardList(int member_id, String page);
	public Map freeCommentList(int member_id, String page);
	public Map photoBoardList(int member_id, String page);
	public Map photoCommentList(int member_id, String page);
	public String searchUserId(String email);
	public String searchUserPwd(String id, String pwdHintAnswer);
	public String getPwdHintQuestion(String id);
	
}