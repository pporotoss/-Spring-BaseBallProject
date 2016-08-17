package com.baseball.notice.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baseball.notice.model.domain.Notice;
import com.baseball.notice.model.repository.NoticeDAO;

import common.Pager;

@Service
public class NoticeServiceImpl implements NoticeService{

	@Autowired
	private NoticeDAO noticeDAO;
	
	@Override
	public Map selectAll(String page) {
		
		if(page == null){
			page = "1";
		}
		
		int pageSize = 10;
		int totalContents = noticeDAO.totalCount();
		int blockSize = 10;
		
		Pager pager = new Pager(Integer.parseInt(page), pageSize, totalContents, blockSize);
		
		Map<String, Integer> paging = new HashMap<String, Integer>();
		paging.put("startContent", pager.getStartContent()-1);
		paging.put("pageSize", pageSize);
				
		List list = noticeDAO.selectAll(paging);
		
		// 전달할 값 설정
		Map<String, Object> map = new HashMap<String, Object>(); 
		map.put("pager", pager);
		map.put("list", list);
		
		return map;
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
