package com.baseball.board.model.repository;

import java.util.List;
import java.util.Map;

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
	public List commentAll(Map paging) {
		
		List list = sqlSessionTemplate.selectList("Comment.selectAll", paging);
		
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

	@Override
	public int deleteByMember(int member_id) {
		
		return sqlSessionTemplate.delete("Comment.deleteByMember", member_id);
	}
	
	@Override
	public int countAll(int board_id) {

		return sqlSessionTemplate.selectOne("Comment.countAll", board_id);
	}

	@Override
	public int userCommentCount(int member_id) {
		
		return sqlSessionTemplate.selectOne("Comment.userCommentCount", member_id);
	}

	@Override
	public List userCommentList(Map user) {

		return sqlSessionTemplate.selectList("Comment.userCommentList", user);
	}

	@Override
	public int deleteCommentByBoard_id(int board_id) {

		return sqlSessionTemplate.delete("Comment.deleteCommentByBoard_id", board_id);
	}

}
