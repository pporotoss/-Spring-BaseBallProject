package com.baseball.member.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.baseball.photoboard.model.domain.PhotoBoard;
import com.baseball.photoboard.model.repository.PhotoBoardDAO;
import com.baseball.photoboard.model.repository.PhotoCommentDAO;
import com.baseball.photoboard.model.service.PhotoBoardService;

import common.GenerateTempPwd;
import common.Pager;

@Service
public class MemberServiceImpl implements MemberService{
	int pageSize = 10;
	int blockSize = 10;
	
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
	
	@Autowired
	PhotoBoardDAO photoBoardDAO;
	
	@Autowired
	PhotoCommentDAO photoCommentDAO;
	
	@Autowired
	PhotoBoardService photoBoardService;

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
	public int registMember(Member member){
		
		String pwd = passwordEncoder.encode(member.getPwd());	// �Ѿ�� ��й�ȣ ��ȣȭ
		member.setPwd(pwd);
		
		String id = member.getId();
		member.setId(id.toLowerCase());// �Է¹��� id�� �ҹ��ڷ� ��ȯ.
		
		return memberDAO.insertMember(member);
	}
	
	// ȸ������ ����
	@Override
	public int updateMember(Member member) {
		
		if(member.getPwd() != null){	// ��й�ȣ ����������,
			String pwd = passwordEncoder.encode(member.getPwd());	// �Ѿ�� ��й�ȣ ��ȣȭ
			member.setPwd(pwd);
		}
		
		return memberDAO.updateMember(member);
	}
	
	// ȸ�� ������ ����
	@Override
	public boolean deleteMember(HttpServletRequest request, int[] member_id) {
		
		int successCount = 0;
		
		for(int i = 0; i < member_id.length; i++){
			
			boardDAO.deleteByMember(member_id[i]); // �ش� ȸ�� �ۼ��� ����
			
			List<PhotoBoard> photoBoardList = photoBoardDAO.userPhotoBoardListAll(member_id[i]);
			for(int pp = 0; pp < photoBoardList.size(); pp++){
				PhotoBoard photoBoard = photoBoardList.get(pp);
				photoBoardService.photoDelete(request, photoBoard);	// �ش� ȸ���� �ø� ���� �� �����.
			}
			
			photoBoardDAO.photoDeleteByMember_id(member_id[i]);
			
			commentDAO.deleteByMember(member_id[i]);
			
			photoCommentDAO.photoCommentDeleteByMember_id(member_id[i]);
			
			successCount += memberDAO.deleteMember(member_id[i]); // �ش� ȸ�� ���� ����
		}
		boolean result = false;
		if(successCount == member_id.length){
			result = true;
		}
		
		return result;
	}
	
	// ȸ�� �Ѹ� ����
	@Override
	public int deleteMember(HttpServletRequest request, int member_id) {
		
		boardDAO.deleteByMember(member_id); // �ش� ȸ�� �ۼ��� ����
		
		List<PhotoBoard> photoBoardList = photoBoardDAO.userPhotoBoardListAll(member_id);
		for(int pp = 0; pp < photoBoardList.size(); pp++){
			PhotoBoard photoBoard = photoBoardList.get(pp);
			photoBoardService.photoDelete(request, photoBoard);	// �ش� ȸ���� �ø� ���� �� �����.
		}
		
		photoBoardDAO.photoDeleteByMember_id(member_id);
		
		commentDAO.deleteByMember(member_id);	// �ش� ȸ�� ��� ����
		
		photoCommentDAO.photoCommentDeleteByMember_id(member_id);
		
		return memberDAO.deleteMember(member_id); // �ش� ȸ�� ���� ����
	}
	
	// �α���ó��!!
	@Override
	public String loginMember(Member member, String rememberId, HttpSession session, HttpServletResponse response){
		
		String referer = null;
		Member loginMember = memberDAO.loginMember(member.getId());
		
		boolean result = passwordEncoder.matches(member.getPwd(), loginMember.getPwd());	// �н����� ��ġ���� Ȯ��.
		
		if(result){	// ��й�ȣ�� ��ġ�ϸ�,
			
			session.setAttribute("loginMember", loginMember); // �α����� ��� ���� ���ǿ� ���!!
			
			Cookie rememberCoookie = new Cookie("REMEMBER", loginMember.getId());
			rememberCoookie.setPath("/");
			
			if(rememberId.equals("on")){		// ���̵� �����ϱ� ����������,
				
				rememberCoookie.setMaxAge(60*60*24*30); // ��Ű�� 30�� ���� ���̵� �����ϱ�.
			
			}else{
				
				rememberCoookie.setMaxAge(0);	// ���̵� �����ϱ� ���� ��������, ��Ű�� ����� ���̵� �����ϱ�.
				
			}
			response.addCookie(rememberCoookie);	// ���䰴ü�� ������ ��Ű ���.
			
			referer = (String)session.getAttribute("referer");	 // ���ǿ� ��Ƴ��� �α��� ������ ���� ������ �ּ� ������!!
			session.removeAttribute("referer"); // ���ǿ��� �������ּ� ����!!
			
			if(referer.equals("/view/member/find_user")){	// ���̵� ã���ؼ� �α���������,
				referer = "/";	// �������� �̵�.
			}
		}// if result
		
		return referer;
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

	@Override
	public Map freeBoardList(int member_id, String page) {
		
		
		int totalContents = boardDAO.userContentsCount(member_id);
		
		if(page == null){
			page = "1";
		}
		
		Pager pager = new Pager(Integer.parseInt(page), pageSize, totalContents, blockSize);
		Map<String, Object> user = new HashMap<>();
		user.put("member_id", member_id);
		user.put("startContent", pager.getStartContent()-1);
		user.put("pageSize", pager.getPageSize());
		
		Map<String, Object> result = new HashMap<>();
		List freeBoardList = boardDAO.userContents(user);
		result.put("freeBoardList", freeBoardList);
		result.put("pager", pager);
		
		return result;
	}

	@Override
	public Map freeCommentList(int member_id, String page) {
		
		int totalContents = commentDAO.userCommentCount(member_id);
		
		if(page == null){
			page = "1";
		}
		
		Pager pager = new Pager(Integer.parseInt(page), pageSize, totalContents, blockSize);
		
		Map<String, Object> user = new HashMap<>();
		user.put("member_id", member_id);
		user.put("startContent", pager.getStartContent()-1);
		user.put("pageSize", pager.getPageSize());
		
		Map<String, Object> result = new HashMap<>();
		List freeCommentList = commentDAO.userCommentList(user);
		result.put("freeCommentList", freeCommentList);
		result.put("pager", pager);
		
		return result;
	}

	@Override
	public Map photoBoardList(int member_id, String page) {
		
		int totalContents = photoBoardDAO.photoBoardCountsByMember_id(member_id);
		if(page == null){
			page = "1";
		}
		
		Pager pager = new Pager(Integer.parseInt(page), pageSize, totalContents, blockSize);
		
		Map<String, Object> user = new HashMap<>();
		user.put("member_id", member_id);
		user.put("startContent", pager.getStartContent()-1);
		user.put("pageSize", pager.getPageSize());
		
		Map<String, Object> result = new HashMap<>();
		List photoBoardList = photoBoardDAO.userPhotoBoardList(user);
		result.put("photoBoardList", photoBoardList);
		result.put("pager", pager);
		
		return result;
	}

	@Override
	public Map photoCommentList(int member_id, String page) {

		int totalContents = photoCommentDAO.userPhotoCommentCounts(member_id);
		if(page == null){
			page = "1";
		}
		
		Pager pager = new Pager(Integer.parseInt(page), pageSize, totalContents, blockSize);
		
		Map<String, Object> user = new HashMap<>();
		user.put("member_id", member_id);
		user.put("startContent", pager.getStartContent()-1);
		user.put("pageSize", pager.getPageSize());
		
		Map<String, Object> result = new HashMap<>();
		List photoCommentList = photoCommentDAO.userPhotoCommentList(user);
		result.put("photoCommentList", photoCommentList);
		result.put("pager", pager);
		
		return result;
	}

	@Override
	public String searchUserId(String email) {
		
		return memberDAO.searchUserId(email);
	}

	@Override
	public String searchUserPwd(String id, String pwdHintAnswer) {
		
		Map<String, Object> user = new HashMap<>();
		user.put("id", id);
		user.put("pwdHintAnswer", pwdHintAnswer);
		
		String pwd = null;
		int resultCount = memberDAO.searchUserPwd(user);
		
		if(resultCount == 1){	// ��ġ�ϸ�,
			 pwd = GenerateTempPwd.generatePwd(10);	// �ӽú�й�ȣ 10�ڸ��� ����.
			
			String encodingPwd = passwordEncoder.encode(pwd);
			
			Map<String, Object> pwdParam = new HashMap<>();
			pwdParam.put("id", id);
			pwdParam.put("pwdHintAnswer", pwdHintAnswer);
			pwdParam.put("pwd", encodingPwd);
			
			memberDAO.saveTempPwd(pwdParam);	// ������ �ӽ� ��� ����.
		}
		
		return pwd;
	}

	@Override
	public String getPwdHintQuestion(String id) {

		return memberDAO.getPwdHintQuestion(id);
	}

	
}
