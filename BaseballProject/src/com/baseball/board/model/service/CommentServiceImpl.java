package com.baseball.board.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baseball.board.model.domain.Comment;
import com.baseball.board.model.domain.CommentDetail;
import com.baseball.board.model.repository.CommentDAO;

import common.Pager;

@Service
public class CommentServiceImpl implements CommentService{

	@Autowired
	private CommentDAO commentDAO;
	
	@Override
	public Map selectAll(int board_id, int page) {
		
		int totalContents = commentDAO.countAll(board_id);
		
		Pager commentPager = new Pager(page, 10, totalContents, 10);
		
		Map<String, Integer> paging = new HashMap<>();
		paging.put("board_id", board_id);
		paging.put("page", commentPager.getStartContent()-1);
						
		List<CommentDetail> originList = commentDAO.commentAll(paging);
		
		ArrayList<CommentDetail> list = new ArrayList<>();	
		
		for(int i = originList.size()-1; i >= 0; i--){ // 페이지마다 불러온 리스트 역순으로 다시 담기.
			CommentDetail commentDetail = originList.get(i);
			list.add(commentDetail);
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("commentPager", commentPager);
		
		return map;
	}

	@Override
	public int update(Comment comment) {

		int result = commentDAO.updateComment(comment);
		
		return result;
	}

	@Override
	public int delete(int comment_id) {

		int result = commentDAO.deleteComment(comment_id);
		
		return result;
	}

	@Override
	public CommentDetail insert(Comment comment) {

		int result = commentDAO.insertComment(comment);
		if(result == 0){
			throw new RuntimeException("등록 실패!!");
		}
		
		CommentDetail commentDetail = commentDAO.selectOne(comment);
		
		return commentDetail;
	}

}
