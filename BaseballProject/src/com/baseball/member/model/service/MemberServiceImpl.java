package com.baseball.member.model.service;

import java.util.List;

import javax.security.auth.login.LoginException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.baseball.exception.LoginFailException;
import com.baseball.member.model.domain.Member;
import com.baseball.member.model.repository.LevelDAO;
import com.baseball.member.model.repository.MemberDAO;

@Service
public class MemberServiceImpl implements MemberService{
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;	// 비밀번호 암호화용 객체
	
	@Autowired
	MemberDAO memberDAO;
	
	@Autowired
	LevelDAO levelDAO;

	@Override
	public List memberList() {

		return memberDAO.selectList();
	}

	@Override
	public List memberAll() {
		
		return memberDAO.memberAll();
	}
	
	@Override
	public List LevelAll() {
		
		return levelDAO.selectAll();
	}	
	
	@Override
	public Member selectMember(int member_id) {
		
		Member member = memberDAO.selectMember(member_id);
		
		return member;
	}

	@Override
	public void registMember(Member member){
		
		String pwd = passwordEncoder.encode(member.getPwd());	// 넘어온 비밀번호 암호화
		member.setPwd(pwd);
		
		String id = member.getId();
		member.setId(id.toLowerCase());// 입력받은 id를 소문자로 변환.
		
		memberDAO.insertMember(member);
		
	}

	@Override
	public void updateMember(Member member) {
		memberDAO.updateMember(member);
	}

	@Override
	public void deleteMember(int[] member_id) {
		
		for(int i = 0; i < member_id.length; i++){
			memberDAO.deleteMember(member_id[i]);
		}
	}

	// 로그인처리!!
	@Override
	public Member loginMember(Member member){
		
		Member repositoryMember = memberDAO.loginMember(member);
		
		boolean result = passwordEncoder.matches(member.getPwd(), repositoryMember.getPwd());
		
		if(!result){	// 비밀번호가 일치하지 않으면,
			throw new LoginFailException("아이디 또는 비밀번호를 확인해 주세요.");
		}
		
		return repositoryMember;
	}

	@Override
	public boolean chkId(String id) {
		boolean duplicate = false;
		
		List<String> idList = memberDAO.getId();
		for(int i = 0; i < idList.size(); i++){
			if(idList.get(i).equals(id)){
				duplicate = true;
			}
		}
		
		return duplicate;
	}

	@Override
	public boolean chkNickname(String nickname) {
		boolean duplicate = false;
		
		List<String> nickList = memberDAO.getNickname();
		for(int i = 0; i < nickList.size(); i++){
			if(nickList.get(i).equals(nickname)){
				duplicate = true;
			}
		}
		
		return duplicate;
	}

	@Override
	public void updateMemberLevel(int[] member_id, int level_id) {
		
		for(int i = 0; i < member_id.length; i++){
			Member member = new Member();
			member.setMember_id(member_id[i]);
			member.setLevel_id(level_id);
			
			memberDAO.updateMemberLevel(member);
		}
		
		
	}

	
}
