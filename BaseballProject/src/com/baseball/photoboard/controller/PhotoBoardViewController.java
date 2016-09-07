package com.baseball.photoboard.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.baseball.photoboard.model.domain.PhotoBoard;
import com.baseball.photoboard.model.domain.PhotoDetail;
import com.baseball.photoboard.model.service.PhotoBoardService;

import common.Searching;

@Controller
public class PhotoBoardViewController {
	
	@Autowired
	PhotoBoardService photoBoardService;
	
	@RequestMapping(value="/photo", method=RequestMethod.GET)	// ���� ���
	public String photoList(Model model, String page, Searching searching){
		
		
		
		return "photo/list";
	}
	
	@RequestMapping(value="/photo", method=RequestMethod.POST)	// �Խù� ���
	public String photoUpload(HttpServletRequest request ,MultipartFile uploadFile, PhotoBoard photoBoard){
		
		int photoBoard_id = photoBoardService.photoUpload(request, uploadFile, photoBoard);
		
		return "redirect:/view/photo/"+photoBoard_id;
	}
	
	@RequestMapping(value="/photo/{photoBoard_id}", method=RequestMethod.GET)
	public String photoLoad(Model model, @PathVariable("photoBoard_id") int photoBoard_id){	// �Խù� �ϳ� �ҷ�����
		
		PhotoDetail photoDetail = photoBoardService.photoLoad(photoBoard_id);
		model.addAttribute("photoDetail", photoDetail);
		
		return "photo/detail";
	}
	
	
}
