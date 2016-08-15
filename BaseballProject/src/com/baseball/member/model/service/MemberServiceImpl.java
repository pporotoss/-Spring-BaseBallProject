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
import com.baseball.member.model.repository.LevelDAO;
import com.baseball.member.model.repository.MemberDAO;

import common.Pager;

@Service
public class MemberServiceImpl implements MemberService{
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;	// 비밀번호 암호화용 객체
	
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

	// 회원정보 불러오기
	@Override
	public Map memberAll(String page, String keyword) {
		List list;
		int pageSize = 10;
		int totalContents = memberDAO.totalMember();
		int blockSize = 10;
		
		if(page == null){
			page = "1";
		}

		Pager pager = new Pager(Integer.parseInt(page), pageSize, totalContents, blockSize);
		
		if(keyword == null){	// 검색어 없을때
		
			list = memberDAO.memberAll(pager.getPage()-1);
			
		}else{	// 검색어 있을때
			
			list = memberDAO.memberSearch(pager.getPage()-1, keyword);
			
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("pager", pager);
		map.put("list", list);
		
		return map;
	}
	
	// 랭크별로 불러오기
	@Override
	public Map memberRank(String page, String rank) {
		
		int pageSize = 10;
		int totalContents = memberDAO.totalMember();
		int blockSize = 10;
		
		if(page == null){
			page = "1";
		}

		Pager pager = new Pager(Integer.parseInt(page), pageSize, totalContents, blockSize);
		
		List list = memberDAO.memberRank(Integer.parseInt(page), Integer.parseInt(rank));
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("pager", pager);
		map.put("list", list);
		
		return map;
	}
	
	@Override
	public List LevelAll() {
		
		return levelDAO.selectAll();
	}	
	
	// 회원정보 한명만 불러오기 
	@Override
	public Member selectMember(int member_id) {
		
		Member member = memberDAO.selectMember(member_id);
		
		return member;
	}

	// 회원 등록
	@Override
	public void registMember(Member member){
		
		String pwd = passwordEncoder.encode(member.getPwd());	// 넘어온 비밀번호 암호화
		member.setPwd(pwd);
		
		String id = member.getId();
		member.setId(id.toLowerCase());// 입력받은 id를 소문자로 변환.
		
		memberDAO.insertMember(member);
		
	}
	
	// 회원정보 변경
	@Override
	public void updateMember(Member member) {
		memberDAO.updateMember(member);
	}
	
	// 회원 여러명 삭제
	@Override
	public void deleteMember(int[] member_id) {
		
		for(int i = 0; i < member_id.length; i++){
			
			memberDAO.deleteMember(member_id[i]); // 해당 회원 정보 삭제
			
			boardDAO.deleteByMember(member_id[i]); // 해당 회원 작성글 삭제
			
			commentDAO.deleteByMember(member_id[i]);
		}
	}
	
	// 회원 한명만 삭제
	@Override
	public void deleteMember(int member_id) {
		
		System.out.println("회원 한명 삭제");
		
		/*memberDAO.deleteMember(member_id); // 해당 회원 정보 삭제
		
		boardDAO.deleteByMember(member_id); // 해당 회원 작성글 삭제
		
		commentDAO.deleteByMember(member_id);	// 해당 회원 댓글 삭제
*/		
	}
	
	// 로그인처리!!
	@Override
	public Member loginMember(Member member){
		
		Member repositoryMember = memberDAO.loginMember(member);
		
		boolean result = passwordEncoder.matches(member.getPwd(), repositoryMember.getPwd());	// 패스워드 일치여부 확인.
		
		if(!result){	// 비밀번호가 일치하지 않으면,
			throw new LoginFailException("아이디 또는 비밀번호를 확인해 주세요.");
		}
		
		return repositoryMember;
	}

	// 아이디 중복 검사
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
	
	// 닉네임 중복 검사
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

	// 멤버 레벨 변경
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
