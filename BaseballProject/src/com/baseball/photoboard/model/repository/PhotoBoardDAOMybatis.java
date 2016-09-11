package com.baseball.photoboard.model.repository;

import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.baseball.photoboard.model.domain.PhotoBoard;
import com.baseball.photoboard.model.domain.PhotoDetail;

import common.Searching;

@Repository
public class PhotoBoardDAOMybatis implements PhotoBoardDAO {
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	@Override
	public Map photoBoardList(String page, Searching searching) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int photoUpload(PhotoBoard photoBoard) {
		
		return sqlSessionTemplate.insert("PhotoBoard.photoUpload", photoBoard);
	}

	@Override
	public PhotoDetail photoLoad(int photoBoard_id) {

		return sqlSessionTemplate.selectOne("PhotoBoard.photoLoad", photoBoard_id);
	}

	@Override
	public int photoEdit(PhotoBoard photoBoard) {
		
		return sqlSessionTemplate.update("PhotoBoard.photoEdit", photoBoard);		
	}

	@Override
	public int photoDelete(int photoBoard_id) {
		
		return sqlSessionTemplate.update("PhotoBoard.photoDelete", photoBoard_id);
	}

	@Override
	public int photoBoardHitUp(int photoBoard_id) {
		
		return sqlSessionTemplate.update("PhotoBoard.photoBoardHitUp", photoBoard_id);		
	}

}
