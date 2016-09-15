package com.baseball.member.model.repository;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.baseball.exception.LoginFailException;
import com.baseball.exception.RegistFailException;
import com.baseball.member.model.domain.Member;
import com.baseball.member.model.domain.MemberDetail;

@Repository
public class MemberDAOMybatis implements MemberDAO{
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	@Override
	public List selectList() {

		List list = sqlSessionTemplate.selectList("Member.selectAll");
		
		return list;
	}
	
	@Override
	public List memberAll(Map searchMap) {
	
		List list = sqlSessionTemplate.selectList("Member.memberAll", searchMap);
		
		return list;
	}
	
	@Override
	public List memberSearch(Map searchMap) {
		
		
		return sqlSessionTemplate.selectList("Member.memberSearch", searchMap);
	}
	
	@Override
	public List memberLevel(Map levelMap) {
		
		
		return sqlSessionTemplate.selectList("Member.memberLevel", levelMap);
	}
	
	@Override
	public MemberDetail selectMember(int member_id) {

		MemberDetail member = sqlSessionTemplate.selectOne("Member.selectOne", member_id);
		return member;
	}

	@Override
	public int insertMember(Member member) {

		int result = sqlSessionTemplate.insert("Member.insert", member);
		if(result == 0){
			throw new RegistFailException("데이터베이스 오류입니다.");
		}
		return result;
	}

	@Override
	public int updateMember(Member member) {
		int result = sqlSessionTemplate.update("Member.update", member);
		
		return result;
	}

	@Override
	public int deleteMember(int member_id) {

		int result = sqlSessionTemplate.delete("Member.delete", member_id);
		return result;
	}

	@Override
	public Member loginMember(String id) {
		
		Member repositoryMember = sqlSessionTemplate.selectOne("Member.login", id);
		
		return repositoryMember;
	}

	@Override
	public List getId() {
				
		return sqlSessionTemplate.selectList("Member.getId");
	}

	@Override
	public List getNickname() {
		
		return sqlSessionTemplate.selectList("Member.getNickname");
	}

	@Override
	public int updateMemberLevel(Member member) {

		return sqlSessionTemplate.update("Member.updateLevel", member);
	}

	@Override
	public int totalMember(Map searchKeyword) {
		
		return sqlSessionTemplate.selectOne("Member.totalMember", searchKeyword);
	}
	
	@Override
	public int totalMember() {
		
		return sqlSessionTemplate.selectOne("Member.totalMember");
	}

	@Override
	public String searchUserId(String email) {

		return sqlSessionTemplate.selectOne("Member.searchUserId", email);
	}

	@Override
	public int searchUserPwd(Map user) {

		return sqlSessionTemplate.selectOne("Member.searchUserPwd", user);
	} 

	@Override
	public String getPwdHintQuestion(String id) {
		
		return sqlSessionTemplate.selectOne("Member.getPwdHintQuestion", id);
	}

	@Override
	public int saveTempPwd(Map pwdParam) {

		return sqlSessionTemplate.update("Member.saveTempPwd", pwdParam);
	}

}
