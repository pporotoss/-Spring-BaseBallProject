package com.baseball.board.model.service;

import java.util.Map;

import com.baseball.board.model.domain.Comment;
import com.baseball.board.model.domain.CommentDetail;

public interface CommentService {
	
	public Map selectAll(int board_id, int commentPage);
	public int update(Comment comment);
	public int delete(int comment_id);
	public int insert(Comment comment);
	
}
