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
	public int photoCommentUpdate(PhotoComment photoComment) {

		return sqlSessionTemplate.update("PhotoComment.photoCommentUpdate", photoComment);
	}

	@Override
	public int photoCommentDelete(int photoComment_id) {

		return sqlSessionTemplate.delete("PhotoComment.photoCommentDelete", photoComment_id);
	}

	@Override
	public int photoCommentCounts(int photoBoard_id) {

		return sqlSessionTemplate.selectOne("PhotoComment.photoCommentCounts", photoBoard_id);
	}

	@Override
	public int photoCommentInsert(PhotoComment photoComment) {

		return sqlSessionTemplate.insert("PhotoComment.photoCommentInsert", photoComment);
	}

	@Override
	public int photoCommentDeleteByPhotoBoard_id(int photoBoard_id) {

		return sqlSessionTemplate.delete("PhotoComment.photoCommentDeleteByPhotoBoard_id", photoBoard_id);
	}

	@Override
	public int photoCommentDeleteByMember_id(int member_id) {

		return sqlSessionTemplate.delete("PhotoComment.photoCommentDeleteByMember_id", member_id);
	}

}
