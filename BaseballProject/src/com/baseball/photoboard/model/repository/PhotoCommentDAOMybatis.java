package com.baseball.photoboard.model.repository;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.baseball.photoboard.model.domain.PhotoComment;

@Repository
public class PhotoCommentDAOMybatis implements PhotoCommentDAO{

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	@Override
	public List photoCommentList(Map parameterMap) {
		
		return sqlSessionTemplate.selectList("PhotoComment.photoCommentList", parameterMap);
	}

	@Override
	public int photoCommentEdit(PhotoComment photoComment) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int photoCommentDelete(int photoComment_id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int photoCommentCounts(int photoBoard_id) {

		return sqlSessionTemplate.selectOne("PhotoComment.photoCommentCounts", photoBoard_id);
	}

	@Override
	public int photoCommentInsert(PhotoComment photoComment) {

		return sqlSessionTemplate.insert("PhotoComment.photoCommentInsert", photoComment);
	}

}
