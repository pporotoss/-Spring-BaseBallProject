package com.baseball.photoboard.model.service;

import java.util.Map;

import com.baseball.photoboard.model.domain.PhotoComment;

public interface PhotoCommentService {
	public Map photoCommentList(int photoBoard_id, int page);
	public void photoCommentEdit(PhotoComment photoComment);
	public void photoCommentDelete(int photoComment_id);
}
