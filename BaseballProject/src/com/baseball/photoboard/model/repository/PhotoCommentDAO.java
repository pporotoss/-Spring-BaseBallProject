package com.baseball.photoboard.model.repository;

import java.util.List;
import java.util.Map;

import com.baseball.photoboard.model.domain.PhotoComment;

public interface PhotoCommentDAO {
	public List photoCommentList(Map parameterMap);
	public int photoCommentUpdate(PhotoComment photoComment);
	public int photoCommentDelete(int photoComment_id);
	public int photoCommentCounts(int photoBoard_id);
	public int photoCommentInsert(PhotoComment photoComment);
	public int photoCommentDeleteByPhotoBoard_id(int photoBoard_id);
	public int photoCommentDeleteByMember_id(int member_id);
	public int userPhotoCommentCounts(int member_id);
	public List userPhotoCommentList(Map user);
}
