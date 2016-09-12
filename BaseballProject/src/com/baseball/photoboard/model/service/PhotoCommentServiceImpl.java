package com.baseball.photoboard.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baseball.photoboard.model.domain.PhotoComment;
import com.baseball.photoboard.model.repository.PhotoCommentDAO;

import common.Pager;

@Service
public class PhotoCommentServiceImpl implements PhotoCommentService{
	
	@Autowired
	private PhotoCommentDAO photoCommentDAO;
	
	@Override
	public Map photoCommentList(int photoBoard_id, int page) {
		
		int pageSize = 10;
		int totalContents = photoCommentDAO.photoCommentCounts(photoBoard_id);
		int blockSize = 10;
		Pager commentPager = new Pager(page, pageSize, totalContents, blockSize);
		
		Map<String, Integer> parameterMap = new HashMap<>();
		parameterMap.put("photoBoard_id", photoBoard_id);
		parameterMap.put("startContent", commentPager.getStartContent()-1);
		parameterMap.put("pageSize", commentPager.getPageSize());
		
		List photoCommentList = photoCommentDAO.photoCommentList(parameterMap);
		
		Map<String, Object> commentMap = new HashMap<>();
		commentMap.put("photoCommentList", photoCommentList);
		commentMap.put("commentPager", commentPager);
		return commentMap;
	}

	@Override
	public int photoCommentUpdate(PhotoComment photoComment) {
		
		return photoCommentDAO.photoCommentUpdate(photoComment);
	}

	@Override
	public int photoCommentDelete(int photoComment_id) {
		
		return photoCommentDAO.photoCommentDelete(photoComment_id);
	}

	@Override
	public int photoCommentInsert(PhotoComment photoComment) {
		
		return photoCommentDAO.photoCommentInsert(photoComment);
	}

}
