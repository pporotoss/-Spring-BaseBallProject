package com.baseball.photoboard.model.repository;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.baseball.photoboard.model.domain.PhotoBoard;
import com.baseball.photoboard.model.domain.PhotoDetail;

@Repository
public class PhotoBoardDAOMybatis implements PhotoBoardDAO {
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	@Override
	public List photoBoardList(Map parameterMap) {

		return sqlSessionTemplate.selectList("PhotoBoard.photoBoardList", parameterMap);
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

	@Override
	public int photoBoardCounts(Map searchMap) {

		return sqlSessionTemplate.selectOne("PhotoBoard.photoBoardCounts", searchMap);
	}

}
