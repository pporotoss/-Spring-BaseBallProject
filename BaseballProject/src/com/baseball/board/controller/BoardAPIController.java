package com.baseball.board.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baseball.board.model.domain.Comment;
import com.baseball.board.model.domain.CommentDetail;
import com.baseball.board.model.service.BoardService;
import com.baseball.board.model.service.CommentService;

/* JSON Ã³¸® Àü¿ë ÄÁÆ®·Ñ·¯ */
@RestController
public class BoardAPIController {
	
	@Autowired
	BoardService boardService;
	
	@Autowired
	CommentService commentService;
	
	// ´ñ±Û ¸ñ·Ï
	@RequestMapping(value="/board/{board_id}/comment", method=RequestMethod.GET, produces="application/json")
	public ResponseEntity<Map<String,Object>> commentList(@PathVariable("board_id") int board_id, String commentPage){
		
		if(commentPage == null){
			commentPage = "1";
		}
		Map<String, Object> commentMap = commentService.selectAll(board_id, Integer.parseInt(commentPage));
		
		return new ResponseEntity<>(commentMap, HttpStatus.OK);
	}
	
	// ´ñ±Û »ðÀÔ
	@RequestMapping(value="/board/{board_id}/comment", method=RequestMethod.POST, produces="application/json")
	public ResponseEntity<Map<String,Object>> insertComment(@PathVariable("board_id") int board_id ,@RequestBody Comment comment){
		
		comment.setBoard_id(board_id);
		commentService.insert(comment);
		
		int page = 1;
		commentService.selectAll(board_id, page);
		
		return new ResponseEntity<Map<String,Object>>(HttpStatus.OK);
	}
	
	// ´ñ±Û ¼öÁ¤
	@RequestMapping(value="/board/{board_id}/comment/{comment_id}", method=RequestMethod.PUT, produces="application/json")
	public ResponseEntity<Comment> updateComment(@PathVariable("board_id") int board_id ,@PathVariable("comment_id") int comment_id, @RequestBody Comment comment){
		
		comment.setComment_id(comment_id);
		commentService.update(comment);
		
		return new ResponseEntity<Comment>(comment, HttpStatus.OK);
	}
	
	
}
