package com.baseball.board.model.repository;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.baseball.board.model.domain.Comment;
import com.baseball.board.model.domain.CommentDetail;

@Repository
public class CommentDAOMybatis implements CommentDAO{
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	@Override
	public List commentAll(int board_id) {
		
		List list = sqlSessionTemplate.selectList("Comment.selectAll", board_id);
		
		return list;
	}
	
	@Override
	public CommentDetail selectOne(Comment comment) {
		
		return sqlSessionTemplate.selectOne("Comment.selectOne", comment);
	}
	
	@Override
	public int insertComment(Comment comment) {
		
		return sqlSessionTemplate.insert("Comment.insert", comment);
	}

	@Override
	public int updateComment(Comment comment) {

		return sqlSessionTemplate.update("Comment.update", comment);		
	}

	@Override
	public int deleteComment(int comment_id) {
		
		return sqlSessionTemplate.delete("Comment.delete", comment_id);
	}

}
