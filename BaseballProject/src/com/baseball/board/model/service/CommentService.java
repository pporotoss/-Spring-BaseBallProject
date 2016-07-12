package com.baseball.board.model.service;

import java.util.List;

import com.baseball.board.model.domain.Comment;
import com.baseball.board.model.domain.CommentDetail;

public interface CommentService {
	
	public List selectAll(int board_id, int page);
	public int update(Comment comment);
	public int delete(int comment_id);
	public CommentDetail insert(Comment comment);
	
}
