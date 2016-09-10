package com.baseball.photoboard.model.repository;

import java.util.List;
import java.util.Map;

import com.baseball.photoboard.model.domain.PhotoComment;

public interface PhotoCommentDAO {
	public List photoCommentList(Map parameterMap);
	public void photoCommentEdit(PhotoComment photoComment);
	public void photoCommentDelete(int photoComment_id);
	public int photoCommentCounts(int photoBoard_id);
}
