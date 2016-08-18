package com.baseball.board.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baseball.board.model.domain.Board;
import com.baseball.board.model.domain.BoardDetail;
import com.baseball.board.model.repository.BoardDAO;
import com.baseball.exception.RegistFailException;

import common.Pager;
import common.Searching;

@Service
public class BoardServiceImpl implements BoardService{
	
	@Autowired
	private BoardDAO boardDAO;
	
	
	@Override
	public Map selectAll(String page, String pagesize, Searching searching) {
		
		if(page == null){
			page = "1";
		}
		if(pagesize == null){
			pagesize = "10";
		}
		
		// 페이징처리
		int blocksize = 10;
		int totalContents = boardDAO.countAll();
		Pager pager = new Pager(Integer.parseInt(page), Integer.parseInt(pagesize), totalContents, blocksize);
		
		// 리스트얻어오기
		Map<String, Object> paging = new HashMap<String, Object>();
		paging.put("startContent", pager.getStartContent()-1);
		paging.put("pagesize", pager.getPageSize());
		
		List list = boardDAO.selectAll(paging);
		
		// 넘겨줄 값 담기.
		Map<String, Object> boardAll = new HashMap<String, Object>();
		boardAll.put("pager", pager);
		boardAll.put("list", list);
		
		return boardAll;
	}

	@Override
	public BoardDetail selectOne(int board_id) {
		
		boardDAO.updateHit(board_id);
		
		return boardDAO.selectOne(board_id);
	}

	@Override
	public void regist(Board board) {
		boardDAO.insert(board);
		if(board.getBoard_id() == 0){
			throw new RegistFailException("등록 실패!!");
		}
		boardDAO.insertKey(board.getBoard_id());
	}
	
	// 답글 삽입
	@Override
	public void replyResist(Board board) {
		int result = -1;
		
		// 원글과 동일한 depth의 답글의 갯수 얻어오기!!
		int depthCount = boardDAO.countDepth(board);  

		if(depthCount > 1){	// 원글과 동일한 단계의 depth가 있으면,

			int minDepthRank = boardDAO.minDepthRank(board);	// 원글과 동일한 depth들 중에서 원글 바로 다음의 rank 가져오기. 
			board.setRank(minDepthRank-1);	// 원글과 동일한 depth들 중에서 원글 바로 다음의 rank인 글부터 rank를 +1 해주기 위해 rank 세팅.
																// 뒤에서 삽입할때 얻어온 rank +1을 해주기때문에 원래의 얻어온 rank로 세팅 안해도 된다.
			boardDAO.rankUpdate(board);
		}else{
			int dPlusOneMaxRank = boardDAO.dPlusOneMaxRank(board);
			board.setRank(dPlusOneMaxRank);
			
		}
		
		boardDAO.replyInsert(board);
		
	}

	@Override
	public void update(Board board) {
		
		boardDAO.update(board);
		
	}

	@Override
	public void delete(int board_id) {
		
		boardDAO.delete(board_id);
		
	}
}
