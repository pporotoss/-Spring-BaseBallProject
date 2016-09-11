package com.baseball.photoboard.model.service;

import java.util.Map;

import com.baseball.photoboard.model.domain.PhotoComment;

public interface PhotoCommentService {
	public Map photoCommentList(int photoBoard_id, int page);
	public int photoCommentEdit(PhotoComment photoComment);
	public int photoCommentDelete(int photoComment_id);
	public int photoCommentInsert(PhotoComment photoComment);
}
