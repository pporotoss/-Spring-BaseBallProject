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

/* �����Խ��� JSON ���� ó��. */
@RestController
public class PhotoBoardAPIController {
	
	@Autowired
	private PhotoCommentService photoCommentService;
	
	@RequestMapping(value="/photo/{photoBoard_id}/comment", method=RequestMethod.GET, produces="application/json")// ��� ����Ʈ �ҷ�����.
	public ResponseEntity<Map<String,Object>> photoCommentList(@PathVariable("photoBoard_id") int photoBoard_id, String commentPage){	
		
		if(commentPage == null){
			commentPage = "1";
		}
		Map<String,Object> commentMap = photoCommentService.photoCommentList(photoBoard_id, Integer.parseInt(commentPage));
		
		return new ResponseEntity<>(commentMap, HttpStatus.OK);
	}
	
	@RequestMapping(value="/photo/{photoBoard_id}/comment", method=RequestMethod.POST, produces="application/json")// ��� ����
	public ResponseEntity<Map> photoCommentInsert(@PathVariable("photoBoard_id") int photoBoard_id, @RequestBody PhotoComment photoComment){
		
		photoComment.setPhotoBoard_id(photoBoard_id);
		int totalPage = photoCommentService.photoCommentInsert(photoComment);
		
		Map commentMap = photoCommentService.photoCommentList(photoBoard_id, totalPage);
		
		return new ResponseEntity<>(commentMap, HttpStatus.OK);
	}
	
	@RequestMapping(value="/photo/{photoBoard_id}/comment/{photoComment_id}", method=RequestMethod.PUT, produces="application/json")// ��� ����
	public ResponseEntity photoCommentUpdate(@PathVariable("photoBoard_id") int photoBoard_id, @PathVariable("photoComment_id") int photoComment_id, @RequestBody PhotoComment photoComment){
		
		photoComment.setPhotoComment_id(photoComment_id);
		
		photoCommentService.photoCommentUpdate(photoComment);
		
		return new ResponseEntity(HttpStatus.OK);
	}
	
	@RequestMapping(value="/photo/{photoBoard_id}/comment/{photoComment_id}", method=RequestMethod.DELETE, produces="application/json")// ��� ����
	public ResponseEntity<Map<String, Object>> photoCommentDelete(@PathVariable("photoBoard_id") int photoBoard_id, @PathVariable("photoComment_id") int photoComment_id){
		
		photoCommentService.photoCommentDelete(photoComment_id);	// ����.
		
		int page = 1;
		Map commentMap = photoCommentService.photoCommentList(photoBoard_id, page);	// ��� �ҷ�����.
		
		return new ResponseEntity<>(commentMap, HttpStatus.OK);
	}
}
