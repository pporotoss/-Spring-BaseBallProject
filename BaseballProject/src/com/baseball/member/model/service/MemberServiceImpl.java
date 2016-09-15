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
	BCryptPasswordEncoder passwordEncoder;	// 비밀번호 암호화용 객체
	
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

	// 회원정보 불러오기
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
		
		if(keyword == null){	// 검색어 없을때
		
			list = memberDAO.memberAll(searchMap);
			
		}else{	// 검색어 있을때
			
			searchMap.put("keyword", keyword);
			list = memberDAO.memberSearch(searchMap);
			
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("pager", pager);
		map.put("list", list);
		
		return map;
	}
	
	// 등급별로 불러오기
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
	
	// 회원정보 한명만 불러오기 
	@Override
	public MemberDetail selectMember(int member_id) {
		
		MemberDetail member = memberDAO.selectMember(member_id);
		
		return member;
	}

	// 회원 등록
	@Override
	public int registMember(Member member){
		
		String pwd = passwordEncoder.encode(member.getPwd());	// 넘어온 비밀번호 암호화
		member.setPwd(pwd);
		
		String id = member.getId();
		member.setId(id.toLowerCase());// 입력받은 id를 소문자로 변환.
		
		return memberDAO.insertMember(member);
	}
	
	// 회원정보 변경
	@Override
	public int updateMember(Member member) {
		
		if(member.getPwd() != null){	// 비밀번호 변경했으면,
			String pwd = passwordEncoder.encode(member.getPwd());	// 넘어온 비밀번호 암호화
			member.setPwd(pwd);
		}
		
		return memberDAO.updateMember(member);
	}
	
	// 회원 여러명 삭제
	@Override
	public boolean deleteMember(HttpServletRequest request, int[] member_id) {
		
		int successCount = 0;
		
		for(int i = 0; i < member_id.length; i++){
			
			boardDAO.deleteByMember(member_id[i]); // 해당 회원 작성글 삭제
			
			List<PhotoBoard> photoBoardList = photoBoardDAO.userPhotoBoardListAll(member_id[i]);
			for(int pp = 0; pp < photoBoardList.size(); pp++){
				PhotoBoard photoBoard = photoBoardList.get(pp);
				photoBoardService.photoDelete(request, photoBoard);	// 해당 회원이 올린 사진 다 지우기.
			}
			
			photoBoardDAO.photoDeleteByMember_id(member_id[i]);
			
			commentDAO.deleteByMember(member_id[i]);
			
			photoCommentDAO.photoCommentDeleteByMember_id(member_id[i]);
			
			successCount += memberDAO.deleteMember(member_id[i]); // 해당 회원 정보 삭제
		}
		boolean result = false;
		if(successCount == member_id.length){
			result = true;
		}
		
		return result;
	}
	
	// 회원 한명만 삭제
	@Override
	public int deleteMember(HttpServletRequest request, int member_id) {
		
		boardDAO.deleteByMember(member_id); // 해당 회원 작성글 삭제
		
		List<PhotoBoard> photoBoardList = photoBoardDAO.userPhotoBoardListAll(member_id);
		for(int pp = 0; pp < photoBoardList.size(); pp++){
			PhotoBoard photoBoard = photoBoardList.get(pp);
			photoBoardService.photoDelete(request, photoBoard);	// 해당 회원이 올린 사진 다 지우기.
		}
		
		photoBoardDAO.photoDeleteByMember_id(member_id);
		
		commentDAO.deleteByMember(member_id);	// 해당 회원 댓글 삭제
		
		photoCommentDAO.photoCommentDeleteByMember_id(member_id);
		
		return memberDAO.deleteMember(member_id); // 해당 회원 정보 삭제
	}
	
	// 로그인처리!!
	@Override
	public String loginMember(Member member, String rememberId, HttpSession session, HttpServletResponse response){
		
		String referer = null;
		Member loginMember = memberDAO.loginMember(member.getId());
		
		boolean result = passwordEncoder.matches(member.getPwd(), loginMember.getPwd());	// 패스워드 일치여부 확인.
		
		if(result){	// 비밀번호가 일치하면,
			
			session.setAttribute("loginMember", loginMember); // 로그인한 멤버 정보 세션에 담기!!
			
			Cookie rememberCoookie = new Cookie("REMEMBER", loginMember.getId());
			rememberCoookie.setPath("/");
			
			if(rememberId.equals("on")){		// 아이디 저장하기 선택했으면,
				
				rememberCoookie.setMaxAge(60*60*24*30); // 쿠키에 30일 동안 아이디 저장하기.
			
			}else{
				
				rememberCoookie.setMaxAge(0);	// 아이디 저장하기 선택 안했으면, 쿠키에 저장된 아이디 삭제하기.
				
			}
			response.addCookie(rememberCoookie);	// 응답객체에 생성한 쿠키 담기.
			
			referer = (String)session.getAttribute("referer");	 // 세션에 담아놓은 로그인 이전에 보던 페이지 주소 얻어오기!!
			session.removeAttribute("referer"); // 세션에서 페이지주소 제거!!
			
			if(referer.equals("/view/member/find_user")){	// 아이디 찾기해서 로그인했으면,
				referer = "/";	// 메인으로 이동.
			}
		}// if result
		
		return referer;
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
		
		if(resultCount == 1){	// 일치하면,
			 pwd = GenerateTempPwd.generatePwd(10);	// 임시비밀번호 10자리수 생성.
			
			String encodingPwd = passwordEncoder.encode(pwd);
			
			Map<String, Object> pwdParam = new HashMap<>();
			pwdParam.put("id", id);
			pwdParam.put("pwdHintAnswer", pwdHintAnswer);
			pwdParam.put("pwd", encodingPwd);
			
			memberDAO.saveTempPwd(pwdParam);	// 생성한 임시 비번 저장.
		}
		
		return pwd;
	}

	@Override
	public String getPwdHintQuestion(String id) {

		return memberDAO.getPwdHintQuestion(id);
	}

	
}
