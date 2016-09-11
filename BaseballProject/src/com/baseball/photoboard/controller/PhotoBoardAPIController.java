package com.baseball.photoboard.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baseball.photoboard.model.domain.PhotoComment;
import com.baseball.photoboard.model.service.PhotoCommentService;

@RestController
public class PhotoBoardAPIController {
	
	@Autowired
	private PhotoCommentService photoCommentService;
	
	@RequestMapping(value="/photo/{photoBoard_id}/comment", method=RequestMethod.GET, produces="application/json")// ´ñ±Û ¸®½ºÆ® ºÒ·¯¿À±â.
	public ResponseEntity<Map<String,Object>> photoCommentList(@PathVariable("photoBoard_id") int photoBoard_id){	
		
		int page = 1;
		Map<String,Object> commentMap = photoCommentService.photoCommentList(photoBoard_id, page);
		
		return new ResponseEntity<>(commentMap, HttpStatus.OK);
	}
	
	@RequestMapping(value="/photo/{photoBoard_id}/comment", method=RequestMethod.POST, produces="application/json")// ´ñ±Û »ðÀÔ
	public ResponseEntity<Map> photoCommentInsert(@PathVariable("photoBoard_id") int photoBoard_id, @RequestBody PhotoComment photoComment){
		
		photoComment.setPhotoBoard_id(photoBoard_id);
		photoCommentService.photoCommentInsert(photoComment);
		
		int page = 1;
		Map commentMap = photoCommentService.photoCommentList(photoBoard_id, page);
		
		return new ResponseEntity<>(commentMap, HttpStatus.OK);
	}
}
