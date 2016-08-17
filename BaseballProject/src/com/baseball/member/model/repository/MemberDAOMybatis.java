package com.baseball.member.model.repository;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.baseball.exception.LoginFailException;
import com.baseball.exception.RegistFailException;
import com.baseball.member.model.domain.Member;

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
	public Member selectMember(int member_id) {

		Member member = sqlSessionTemplate.selectOne("Member.selectOne", member_id);
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
	public Member loginMember(Member member) {
		
		Member repositoryMember = sqlSessionTemplate.selectOne("Member.login", member);
		
		if(repositoryMember == null){	// 해당 아이디가 존재하지 않으면,
			throw new LoginFailException("아이디 또는 비밀번호를 확인해주세요.");
		}
		
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
	public int totalMember() {
		
		return sqlSessionTemplate.selectOne("Member.totalMember");
	}

}
