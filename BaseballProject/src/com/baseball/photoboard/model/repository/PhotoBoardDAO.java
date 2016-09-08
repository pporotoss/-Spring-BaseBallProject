package com.baseball.photoboard.model.repository;

import java.util.Map;

import com.baseball.photoboard.model.domain.PhotoBoard;
import com.baseball.photoboard.model.domain.PhotoDetail;

import common.Searching;

public interface PhotoBoardDAO {
	
	public Map photoBoardList(String page, Searching searching);
	public int photoUpload(PhotoBoard photoBoard);
	public PhotoDetail photoLoad(int photoBoard_id);
	public void photoEdit(PhotoBoard photoBoard);
	public void photoDelete(int photoBoard_id);
	public void photoBoardHitUp(int photoBoard_id);
	
}
