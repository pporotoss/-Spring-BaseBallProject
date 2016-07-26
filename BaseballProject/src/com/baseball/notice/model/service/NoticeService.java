package com.baseball.notice.model.service;

import java.util.Map;

import com.baseball.notice.model.domain.Notice;

public interface NoticeService {

	public Map selectAll(String page);
	public Notice selectOne(int notice_id);
	public void update(Notice notice);
	public void delete(int notice_id);
	public void insert(Notice notice);
	
}
