package com.baseball.member.model.repository;

import java.util.List;
import java.util.Map;

import com.baseball.member.model.domain.Member;

public interface MemberDAO {
	
	public List selectList();	// 멤버만 불러오기
	public List memberAll(Map searchMap);	// 멤버 관련 정보까지 불러오기
	public List memberSearch(Map searchMap);	// 멤버 검색하기
	public List memberLevel(Map levelMap);	// 랭크별로 불러오기
	public int totalMember();	// 총 멤버수 불러오기.
	public Member selectMember(int member_id);
	public int insertMember(Member member);
	public int updateMember(Member member);
	public int deleteMember(int member_id);
	public Member loginMember(Member member);
	public List getId();
	public List getNickname();
	public int updateMemberLevel(Member member);
}
