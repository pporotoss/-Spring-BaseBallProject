package com.baseball.photoboard.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baseball.photoboard.model.domain.PhotoComment;
import com.baseball.photoboard.model.domain.PhotoCommentDetail;

@RestController
public class PhotoBoardAPIController {
	
	@RequestMapping(value="/photo/{photoBoard_id}/comment", method=RequestMethod.GET, produces="applicaion/json")// 댓글 리스트 불러오기.
	public ResponseEntity<PhotoCommentDetail> photoCommentList(Model model){	
		
		return null;
	}
}
