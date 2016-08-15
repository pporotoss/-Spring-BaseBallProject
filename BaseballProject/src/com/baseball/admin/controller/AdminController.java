package com.baseball.admin.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.baseball.member.model.repository.LevelDAO;
import com.baseball.member.model.service.MemberService;

/* 관리자 전용 요청 */
@Controller
public class AdminController {
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private LevelDAO levelDAO;
	
	// 회원 불러오기
	@RequestMapping(value="/admin/member", method=RequestMethod.GET)
	public String goAdmin(Model model, String page, String rank, String keyword){
		Map map;

		if(rank == null){
			
			map = memberService.memberAll(page, keyword);
			
		}else{
			
			map = memberService.memberRank(page, rank);
			
		}
		
		List memberList = (List)map.get("list");
		
		model.addAttribute("memberList", memberList);
		
		List levelList = levelDAO.selectAll();
		
		model.addAttribute("levelList", levelList);
		
		return "admin/member";
	}
	
	// 회원 삭제
	@RequestMapping(value="/admin/member", method=RequestMethod.DELETE)
	public String deleteMember(int[] member_id){
		
		memberService.deleteMember(member_id);
		
		return "redirect:/view/admin/member";
	}
	
	// 회원 등급 변경
	@RequestMapping(value="/admin/member", method=RequestMethod.PUT)
	public String updateLevel(int[] member_id, int level_id){
		
		memberService.updateMemberLevel(member_id, level_id);
		
		return "redirect:/view/admin/member";
	}
}
