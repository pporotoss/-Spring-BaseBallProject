package com.baseball.photoboard.model.repository;

import java.util.List;
import java.util.Map;

import com.baseball.photoboard.model.domain.PhotoBoard;
import com.baseball.photoboard.model.domain.PhotoDetail;

public interface PhotoBoardDAO {
	
	public List photoBoardList(Map parameterMap);
	public int photoUpload(PhotoBoard photoBoard);
	public PhotoDetail photoLoad(int photoBoard_id);
	public int photoEdit(PhotoBoard photoBoard);
	public int photoDelete(int photoBoard_id);
	public int photoBoardHitUp(int photoBoard_id);
	public int photoBoardCounts(Map searchMap);
	public int photoDeleteByMember_id(int member_id);
}
