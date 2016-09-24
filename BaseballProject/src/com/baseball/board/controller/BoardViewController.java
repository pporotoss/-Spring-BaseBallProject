package com.baseball.board.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.baseball.board.model.domain.Board;
import com.baseball.board.model.domain.BoardDetail;
import com.baseball.board.model.service.BoardService;
import com.baseball.board.model.service.CommentService;

import common.Pager;
import common.Searching;

@Controller
public class BoardViewController {
	
	private String TAG = this.getClass().getName();
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private CommentService commentService;
	
	// ����Ʈ�������� �̵�
	@RequestMapping(value="/board", method=RequestMethod.GET)
	public String selectAll(Model model, String page, String pagesize, Searching searching){
		
		Map<String, Object> boardAll = 	boardAll = boardService.selectAll(page, pagesize, searching);
		
		if(searching.getKeyword() != null){	// �˻��� ������,
			model.addAttribute("searching", searching);
		}
		
		// ����Ʈ ������
		List<BoardDetail> boardDetailList = (List)boardAll.get("list");
		model.addAttribute("boardDetailList", boardDetailList);
		
		// ����¡ó��
		Pager pager = (Pager)boardAll.get("pager");
		model.addAttribute("pager", pager);
		
		return "board/list";
	}
	
	// �۵���ϱ�
	@RequestMapping(value="/board", method=RequestMethod.POST)
	public String regist(Board board){
		
		boardService.regist(board);
		
		return "redirect:/view/board/"+board.getBoard_id();	// �������� �̵�.
	}
	
	// �� ����������� �̵�
	@RequestMapping(value="/board/write")
	public String write(){
		
		return "board/write";
	}
	
	// ���������� �̵�
	@RequestMapping(value="/board/{board_id}", method=RequestMethod.GET)
	public String detail(@PathVariable("board_id") int board_id, String commentPage, String page, String pagesize, Searching searching, Model model){
		
		BoardDetail detail = boardService.selectOne(board_id);
		model.addAttribute("detail", detail);
		
		if(commentPage == null){
			commentPage = "1";
		}
		
		Map<String, Object> map = commentService.selectAll(board_id, Integer.parseInt(commentPage));
		
		List commentList = (List)map.get("commentList");
		Pager commentPager = (Pager) map.get("commentPager");
		
		model.addAttribute("commentList", commentList);
		model.addAttribute("commentPager", commentPager);
		
		model.addAttribute("page", page);
		model.addAttribute("pagesize", pagesize);
		if(searching.getKeyword() != null){
			model.addAttribute("searching", searching);
		}
		
		return "board/detail";
	}
	
	// �� ���� �������� �̵�
	@RequestMapping(value="/board/{board_id}", method=RequestMethod.POST)
	public String goEdit(@PathVariable("board_id") int board_id, Model model){
		
		BoardDetail boardDetail = boardService.selectOne(board_id);
		
		model.addAttribute("boardDetail", boardDetail);
		
		return "board/edit";
	}
	
	// �� ����
	@RequestMapping(value="/board/{board_id}", method=RequestMethod.PUT)
	public String edit(@PathVariable("board_id") int board_id, Board board){
		
		boardService.update(board);
		
		return "redirect:/view/board/"+board_id;
	}
	
	// �� ����
	@RequestMapping(value="/board/{board_id}", method=RequestMethod.DELETE)
	public String delete(@PathVariable("board_id") int board_id){
		boardService.delete(board_id);
		
		return "redirect:/view/board";
	}
	
	
	// ��۵�� �������� �̵�
	@RequestMapping(value="/board/reply/write", method=RequestMethod.POST)
	public String getReply(Model model, Board board){
		
		model.addAttribute("board", board);
		
		return "board/reply";
	}
	
	// ��� ���!!
	@RequestMapping(value="/board/reply", method=RequestMethod.POST)
	public String registReply(Board board){
		
		boardService.replyResist(board);
		
		return "redirect:/view/board";
	}
	
	
}
