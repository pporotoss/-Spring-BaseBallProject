package com.baseball.board.controller;

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
	
	
	// ´ñ±Û »ðÀÔ ¹× »ðÀÔ µÈ ´ñ±Û ¹ÝÈ¯.
	@RequestMapping(value="/board/*/comment", method=RequestMethod.POST, produces="application/json")
	public ResponseEntity<CommentDetail> insertComment(@RequestBody Comment comment){
		
		CommentDetail commentDetail = commentService.insert(comment);
		
		return new ResponseEntity<CommentDetail>(commentDetail, HttpStatus.OK);
	}
	
	// ´ñ±Û ¼öÁ¤
	@RequestMapping(value="/board/*/comment/{comment_id}", method=RequestMethod.PUT, produces="application/json")
	public ResponseEntity<Comment> updateComment(@PathVariable("comment_id") int comment_id, @RequestBody Comment comment){
		
		comment.setComment_id(comment_id);
		commentService.update(comment);
		
		return new ResponseEntity<Comment>(comment, HttpStatus.OK);
	}
	
	
}
