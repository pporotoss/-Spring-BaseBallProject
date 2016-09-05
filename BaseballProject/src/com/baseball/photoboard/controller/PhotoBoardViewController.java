package com.baseball.photoboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import common.Searching;

@Controller
public class PhotoBoardViewController {

	@RequestMapping("/photo")	// 사진 목록
	public String photoList(Model model, String page, Searching searching){
		
		
		
		return "photo/list";
	}
	
	@RequestMapping(value="/photo/upload/{member_id}", method=RequestMethod.GET)
	public String photoUpload(Model model, @PathVariable("member_id") int member_id, MultipartFile uploadFile){
		
		
		
		return "photo/upload";
	}
	
}
