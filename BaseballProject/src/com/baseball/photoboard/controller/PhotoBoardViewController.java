package com.baseball.photoboard.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.baseball.photoboard.model.domain.PhotoBoard;
import com.baseball.photoboard.model.domain.PhotoCommentDetail;
import com.baseball.photoboard.model.domain.PhotoDetail;
import com.baseball.photoboard.model.service.PhotoBoardService;
import com.baseball.photoboard.model.service.PhotoCommentService;

import common.Pager;
import common.Searching;

@Controller
public class PhotoBoardViewController {
	
	@Autowired
	PhotoBoardService photoBoardService;
	@Autowired
	PhotoCommentService photoCommentService;
	
	@RequestMapping(value="/photo", method=RequestMethod.GET)	// 사진 목록
	public String photoList(Model model, String page, Searching searching){
		
		if(page == null){
			page = "1";
		}
		Map<String, Object> photoBoardMap = photoBoardService.photoBoardList(Integer.parseInt(page), searching);
		List photoBoardList = (List)photoBoardMap.get("photoBoardList");
		Pager pager = (Pager) photoBoardMap.get("pager");
		
		if(searching.getKeyword() != null){	// 검색어 있으면
			model.addAttribute("searching", searching);
		}
		
		model.addAttribute("photoBoardList", photoBoardList);
		model.addAttribute("pager", pager);
		return "photo/list";
	}
	
	@RequestMapping(value="/photo", method=RequestMethod.POST)	// 게시물 등록
	public String photoUpload(HttpServletRequest request ,MultipartFile uploadFile, PhotoBoard photoBoard){
		
		int photoBoard_id = photoBoardService.photoUpload(request, uploadFile, photoBoard);
		
		return "redirect:/view/photo/"+photoBoard_id;
	}
	
	@RequestMapping(value="/photo/{photoBoard_id}", method=RequestMethod.GET)
	public String photoLoad(Model model, @PathVariable("photoBoard_id") int photoBoard_id){	// 게시물 하나 불러오기
		
		PhotoDetail photoDetail = photoBoardService.photoLoad(photoBoard_id);	// 게시물 불러오기.
		
		int page = 1;
		Map commentMap = photoCommentService.photoCommentList(photoBoard_id, page);// 댓글 불러오기.
		
		List photoCommentList = (List)commentMap.get("photoCommentList");
		Pager commentPager = (Pager) commentMap.get("commentPager");
		
		model.addAttribute("photoDetail", photoDetail);
		model.addAttribute("photoCommentList", photoCommentList);
		model.addAttribute("commentPager", commentPager);
		
		return "photo/detail";
	}
	
	@RequestMapping(value="/photo/{photoBoard_id}", method=RequestMethod.POST)	// 게시물 수정하기. multipart/form은 무조껀 POST로만 하도록 Apache에서 강제!!
	public String photoEdit(Model model, @PathVariable("photoBoard_id") int photoBoard_id, HttpServletRequest request, MultipartFile uploadFile, PhotoBoard photoBoard){
		
		photoBoard.setPhotoBoard_id(photoBoard_id);
		photoBoardService.photoEdit(request, uploadFile, photoBoard);
		
		return "redirect:/view/photo/"+photoBoard_id;
	}
	
	@RequestMapping(value="/photo/{photoBoard_id}", method=RequestMethod.DELETE)// 게시물 삭제.
	public String photoDelete(HttpServletRequest request, @PathVariable("photoBoard_id") int photoBoard_id, PhotoBoard photoBoard){	
		
		photoBoard.setPhotoBoard_id(photoBoard_id);
		photoBoardService.photoDelete(request, photoBoard);
				
		return "redirect:/view/photo";
	}
	
	@RequestMapping(value="/photo/edit", method=RequestMethod.POST)
	public String goEdit(Model model, PhotoDetail photoDetail ){	// 수정하기 페이지로 이동.
		
		model.addAttribute("photoDetail", photoDetail);
		
		return "photo/edit";
	}
	
	
}
