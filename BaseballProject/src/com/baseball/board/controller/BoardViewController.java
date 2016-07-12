package com.baseball.board.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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

@Controller
public class BoardViewController {
	
	private String TAG = this.getClass().getName();
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private CommentService commentService;
	
	// 리스트페이지로 이동
	@RequestMapping(value="/board", method=RequestMethod.GET)
	public String selectAll(Model model, String page, String pagesize){
		
		Map<String, Object> boardAll = boardService.selectAll(page, pagesize);
		
		// 리스트 얻어오기
		List<BoardDetail> boardDetailList = (List)boardAll.get("list");
		model.addAttribute("boardDetailList", boardDetailList);
		
		// 페이징처리
		Pager pager = (Pager)boardAll.get("pager");
		model.addAttribute("pager", pager);
		
		return "board/list";
	}
	
	// 글등록하기
	@RequestMapping(value="/board", method=RequestMethod.POST)
	public String regist(Board board){
		
		boardService.regist(board);
		
		return "redirect:/view/board";
	}
	
	// 글 등록페이지로 이동
	@RequestMapping(value="/board/write")
	public String write(){
		
		return "board/write";
	}
	
	// 상세페이지로 이동
	@RequestMapping(value="/board/{board_id}", method=RequestMethod.GET)
	public String detail(@PathVariable("board_id") int board_id, Model model){
		
		BoardDetail detail = boardService.selectOne(board_id);
		model.addAttribute("detail", detail);
		
		List commentList = commentService.selectAll(board_id, 1);
		model.addAttribute("commentList", commentList);
		
		return "board/detail";
	}
	
	
	// 글 수정 페이지로 이동
	@RequestMapping(value="/board/{board_id}", method=RequestMethod.POST)
	public String goEdit(@PathVariable("board_id") int board_id, Model model){
		
		BoardDetail boardDetail = boardService.selectOne(board_id);
		
		model.addAttribute("boardDetail", boardDetail);
		
		return "board/edit";
	}
	
	// 글 수정
	@RequestMapping(value="/board/{board_id}", method=RequestMethod.PUT)
	public String edit(@PathVariable("board_id") int board_id, Board board){
		
		boardService.update(board);
		
		return "redirect:/view/board/"+board_id;
	}
	
	// 글 삭제
	@RequestMapping(value="/board/{board_id}", method=RequestMethod.DELETE)
	public String delete(@PathVariable("board_id") int board_id){
		
		boardService.delete(board_id);
		
		return "redirect:/view/board";
	}
	
	
	// 답글등록 페이지로 이동
	@RequestMapping(value="/board/reply/write", method=RequestMethod.POST)
	public String getReply(Model model, Board board){
		
		model.addAttribute("board", board);
		
		return "board/reply";
	}
	
	// 답글 등록!!
	@RequestMapping(value="/board/reply", method=RequestMethod.POST)
	public String registReply(Board board){
		
		boardService.replyResist(board);
		
		return "redirect:/view/board";
	}
	
	// 댓글삭제!!
	@RequestMapping(value="/board/{board_id}/comment/{comment_id}", method=RequestMethod.DELETE)
	public String deleteComment(@PathVariable("board_id") int board_id, @PathVariable("comment_id") int comment_id){
		
		commentService.delete(comment_id);
		
		return "redirect:/view/board/"+board_id;
	}
	
}
