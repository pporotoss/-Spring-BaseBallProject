package com.baseball.member.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.baseball.board.model.repository.BoardDAO;
import com.baseball.board.model.repository.CommentDAO;
import com.baseball.exception.LoginFailException;
import com.baseball.member.model.domain.Member;
import com.baseball.member.model.repository.LevelDAO;
import com.baseball.member.model.repository.MemberDAO;

@Service
public class MemberServiceImpl implements MemberService{
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;	// ��й�ȣ ��ȣȭ�� ��ü
	
	@Autowired
	MemberDAO memberDAO;
	
	@Autowired
	LevelDAO levelDAO;
	
	@Autowired
	BoardDAO boardDAO;
	
	@Autowired
	CommentDAO commentDAO;

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
	
	// ȸ������ �Ѹ� �ҷ����� 
	@Override
	public Member selectMember(int member_id) {
		
		Member member = memberDAO.selectMember(member_id);
		
		return member;
	}

	// ȸ�� ���
	@Override
	public void registMember(Member member){
		
		String pwd = passwordEncoder.encode(member.getPwd());	// �Ѿ�� ��й�ȣ ��ȣȭ
		member.setPwd(pwd);
		
		String id = member.getId();
		member.setId(id.toLowerCase());// �Է¹��� id�� �ҹ��ڷ� ��ȯ.
		
		memberDAO.insertMember(member);
		
	}
	
	// ȸ������ ����
	@Override
	public void updateMember(Member member) {
		memberDAO.updateMember(member);
	}
	
	// ȸ�� ������ ����
	@Override
	public void deleteMember(int[] member_id) {
		
		for(int i = 0; i < member_id.length; i++){
			
			memberDAO.deleteMember(member_id[i]); // �ش� ȸ�� ���� ����
			
			boardDAO.deleteByMember(member_id[i]); // �ش� ȸ�� �ۼ��� ����
			
			commentDAO.deleteByMember(member_id[i]);
		}
	}
	
	// ȸ�� �Ѹ� ����
	@Override
	public void deleteMember(int member_id) {
		
		System.out.println("ȸ�� �Ѹ� ����");
		
		/*memberDAO.deleteMember(member_id); // �ش� ȸ�� ���� ����
		
		boardDAO.deleteByMember(member_id); // �ش� ȸ�� �ۼ��� ����
		
		commentDAO.deleteByMember(member_id);	// �ش� ȸ�� ��� ����
*/		
	}
	
	// �α���ó��!!
	@Override
	public Member loginMember(Member member){
		
		Member repositoryMember = memberDAO.loginMember(member);
		
		boolean result = passwordEncoder.matches(member.getPwd(), repositoryMember.getPwd());	// �н����� ��ġ���� Ȯ��.
		
		if(!result){	// ��й�ȣ�� ��ġ���� ������,
			throw new LoginFailException("���̵� �Ǵ� ��й�ȣ�� Ȯ���� �ּ���.");
		}
		
		return repositoryMember;
	}

	// ���̵� �ߺ� �˻�
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
	
	// �г��� �ߺ� �˻�
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

	// ��� ���� ����
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
