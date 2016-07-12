package com.baseball.board.model.repository;

import java.util.List;

import com.baseball.board.model.domain.Comment;
import com.baseball.board.model.domain.CommentDetail;

public interface CommentDAO {
	
	public List commentAll(int board_id);
	public CommentDetail selectOne(Comment comment);
	public int insertComment(Comment comment);
	public int updateComment(Comment comment);
	public int deleteComment(int comment_id);
	
}
