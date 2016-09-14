package com.baseball.admin.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.baseball.member.model.repository.LevelDAO;
import com.baseball.member.model.service.MemberService;

import common.Pager;
import common.Searching;

/* ������ ���� ��û */
@Controller
public class AdminController {
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private LevelDAO levelDAO;
	
	// ȸ�� �ҷ�����
	@RequestMapping(value="/admin/member", method=RequestMethod.GET)
	public String goAdmin(Model model, String page, String level_id, String keyword){
		Map map;
		
		if(level_id == null || level_id.equals("all")){

			map = memberService.memberAll(page, keyword);
			
			if(keyword != null){	 // �˻��� ������ 
				model.addAttribute("keyword", keyword);
			}
			
		}else{
			
			map = memberService.memberLevel(page, level_id);
			model.addAttribute("level_id", level_id);	// ��޺��� ǥ���Ҷ���.
			
		}
		
		Pager pager = (Pager)map.get("pager");
		model.addAttribute("pager", pager);
		
		List memberList = (List)map.get("list");
		
		model.addAttribute("memberList", memberList);
		
		List levelList = levelDAO.selectAll();
		
		model.addAttribute("levelList", levelList);
		
		return "admin/member";
	}
	
	// ȸ�� ����
	@RequestMapping(value="/admin/member", method=RequestMethod.DELETE)
	public String deleteMember(HttpServletRequest request, int[] member_id){
		
		memberService.deleteMember(request, member_id);
		
		return "redirect:/view/admin/member";
	}
	
	// ȸ�� ��� ����
	@RequestMapping(value="/admin/member", method=RequestMethod.PUT)
	public String updateLevel(int[] member_id, int level_id){
		
		memberService.updateMemberLevel(member_id, level_id);
		
		return "redirect:/view/admin/member";
	}
}
