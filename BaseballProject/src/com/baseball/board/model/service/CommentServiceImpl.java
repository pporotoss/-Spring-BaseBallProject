package com.baseball.board.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baseball.board.model.domain.Comment;
import com.baseball.board.model.domain.CommentDetail;
import com.baseball.board.model.repository.CommentDAO;

@Service
public class CommentServiceImpl implements CommentService{

	@Autowired
	private CommentDAO commentDAO;
	
	@Override
	public List selectAll(int board_id, int page) {
		
		Map<String, Integer> paging = new HashMap<>();
		paging.put("board_id", board_id);
		paging.put("page", page-1);
		
		List list = commentDAO.commentAll(paging);
		
		return list;
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
