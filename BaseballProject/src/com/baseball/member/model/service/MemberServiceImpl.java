package com.baseball.member.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.baseball.board.model.repository.BoardDAO;
import com.baseball.board.model.repository.CommentDAO;
import com.baseball.exception.LoginFailException;
import com.baseball.member.model.domain.Member;
import com.baseball.member.model.domain.MemberDetail;
import com.baseball.member.model.repository.LevelDAO;
import com.baseball.member.model.repository.MemberDAO;

import common.Pager;

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

	// ȸ������ �ҷ�����
	@Override
	public Map memberAll(String page, String keyword) {
		List list;
		int pageSize = 10;
		Map searchKeyword = new HashMap<>();
		searchKeyword.put("keyword", keyword);
		int totalContents = memberDAO.totalMember(searchKeyword);
		int blockSize = 10;
		
		if(page == null){
			page = "1";
		}

		Pager pager = new Pager(Integer.parseInt(page), pageSize, totalContents, blockSize);
		Map searchMap = new HashMap<>();
		searchMap.put("startContent", pager.getStartContent()-1);
		searchMap.put("pageSize", pageSize);
		
		if(keyword == null){	// �˻��� ������
		
			list = memberDAO.memberAll(searchMap);
			
		}else{	// �˻��� ������
			
			searchMap.put("keyword", keyword);
			list = memberDAO.memberSearch(searchMap);
			
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("pager", pager);
		map.put("list", list);
		
		return map;
	}
	
	// ��޺��� �ҷ�����
	@Override
	public Map memberLevel(String page, String level_id) {
		
		int pageSize = 10;
		int totalContents = memberDAO.totalMember();
		int blockSize = 10;
		
		if(page == null){
			page = "1";
		}

		Pager pager = new Pager(Integer.parseInt(page), pageSize, totalContents, blockSize);
		
		Map levelMap = new HashMap<>();
		levelMap.put("startContent", pager.getStartContent()-1);
		levelMap.put("pageSize", pageSize);
		levelMap.put("level_id", Integer.parseInt(level_id));
		
		List list = memberDAO.memberLevel(levelMap);
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("pager", pager);
		map.put("list", list);
		
		return map;
	}
	
	@Override
	public List LevelAll() {
		
		return levelDAO.selectAll();
	}	
	
	// ȸ������ �Ѹ� �ҷ����� 
	@Override
	public MemberDetail selectMember(int member_id) {
		
		MemberDetail member = memberDAO.selectMember(member_id);
		
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
		
		if(member.getPwd() != null){	// ��й�ȣ ����������,
			String pwd = passwordEncoder.encode(member.getPwd());	// �Ѿ�� ��й�ȣ ��ȣȭ
			member.setPwd(pwd);
		}
		
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
