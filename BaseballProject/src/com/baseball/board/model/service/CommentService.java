package com.baseball.board.model.service;

import java.util.Map;

import com.baseball.board.model.domain.Comment;
import com.baseball.board.model.domain.CommentDetail;

public interface CommentService {
	
	public Map selectAll(int board_id, int page);
	public int update(Comment comment);
	public int delete(int comment_id);
	public CommentDetail insert(Comment comment);
	
}
