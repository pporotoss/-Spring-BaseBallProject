package com.baseball.notice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.baseball.notice.model.domain.Notice;
import com.baseball.notice.model.service.NoticeService;

@Controller
public class NoticeViewController {

	@Autowired
	private NoticeService noticeService;
	
	// 공지사항 리스트 이동
	@RequestMapping(value="/notice", method=RequestMethod.GET)
	public String noticeList(Model model){
		
		List noticeList = noticeService.selectAll();
		
		model.addAttribute("noticeList", noticeList);
		
		return "notice/list";
	}
	
	// 공지사항 쓰기 페이지 이동
	@RequestMapping("/notice/write")
	public String goWrite(){
				
		return "notice/write";
	}
	
	// 공지사항 등록
	@RequestMapping(value="/notice", method=RequestMethod.POST)
	public String regist(Notice notice){
		
		noticeService.insert(notice);
		
		return "redirect:/view/notice";
	}
	
	// 공지사항 상세페이지
	@RequestMapping(value="/notice/{notice_id}", method=RequestMethod.GET)
	public String goDetail(@PathVariable("notice_id") int notice_id, Model model){
		
		Notice notice = noticeService.selectOne(notice_id);
		model.addAttribute("notice", notice);
		
		return "notice/detail";
	}
	
	// 공지사항 수정페이지
	@RequestMapping(value="/notice/{notice_id}", method=RequestMethod.POST)
	public String goEdit(@PathVariable("notice_id") int notice_id, Model model){
		
		Notice notice = noticeService.selectOne(notice_id);
		
		model.addAttribute("notice", notice);
		
		return "notice/edit";
	}
	
	// 공지사항 수정하기!!
	@RequestMapping(value="/notice/{notice_id}", method=RequestMethod.PUT)
	public String update(@PathVariable("notice_id") int notice_id, Notice notice){
		
		noticeService.update(notice);
		
		return "redirect:/view/notice/"+notice_id;
	}
	
	// 공지사항 삭제하기
	@RequestMapping(value="notice/{notice_id}", method=RequestMethod.DELETE)
	public String delete(@PathVariable("notice_id") int notice_id){
		
		noticeService.delete(notice_id);
		
		return "redirect:/view/notice";
	}
}
