package com.baseball.photo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PhotoViewController {

	@RequestMapping("/photo")
	public String photoList(){
		
		return "photo/list";
	}
}
