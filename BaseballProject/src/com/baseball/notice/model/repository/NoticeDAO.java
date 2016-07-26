package com.baseball.notice.model.repository;

import java.util.List;
import java.util.Map;

import com.baseball.notice.model.domain.Notice;

public interface NoticeDAO {
	
	public List selectAll(Map paing);
	public Notice selectOne(int notice_id);
	public int totalCount();
	public void update(Notice notice);
	public void delete(int notice_id);
	public void insert(Notice notice);
	public void updateHit(int notice_id);
}
