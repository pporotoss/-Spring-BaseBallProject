package com.baseball.notice.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baseball.notice.model.domain.Notice;
import com.baseball.notice.model.repository.NoticeDAO;

@Service
public class NoticeServiceImpl implements NoticeService{

	@Autowired
	private NoticeDAO noticeDAO;
	
	@Override
	public List selectAll() {
		
		List list = noticeDAO.selectAll();
		
		return list;
	}

	@Override
	public Notice selectOne(int notice_id) {
		
		noticeDAO.updateHit(notice_id);
		
		Notice notice = noticeDAO.selectOne(notice_id);
		
		return notice;
	}

	@Override
	public void update(Notice notice) {
		
		noticeDAO.update(notice);
	}

	@Override
	public void delete(int notice_id) {

		noticeDAO.delete(notice_id);
	}

	@Override
	public void insert(Notice notice) {
		
		noticeDAO.insert(notice);	
	}


}
