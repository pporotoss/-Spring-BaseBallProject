package com.baseball.board.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baseball.board.model.domain.Board;
import com.baseball.board.model.domain.BoardDetail;
import com.baseball.board.model.repository.BoardDAO;
import com.baseball.board.model.repository.CommentDAO;
import com.baseball.exception.RegistFailException;

import common.Pager;
import common.Searching;

@Service
public class BoardServiceImpl implements BoardService{
	
	@Autowired
	private BoardDAO boardDAO;
	
	@Autowired
	private CommentDAO commentDAO;
	
	
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
		int totalContents = boardDAO.countAll(searching);
		Pager pager = new Pager(Integer.parseInt(page), Integer.parseInt(pagesize), totalContents, blocksize);
		
		// 리스트얻어오기
		Map<String, Object> paging = new HashMap<String, Object>();
		paging.put("startContent", pager.getStartContent()-1);
		paging.put("pagesize", pager.getPageSize());
		
		if(searching.getKeyword() != null){
			paging.put("searching", searching);
		}
		
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
		
		boardDAO.insert(board);	// 글 등록.
		
		if(board.getBoard_id() == 0){
			throw new RegistFailException("등록 실패!!");
		}
		boardDAO.setFamily(board.getBoard_id());	//	등록 글 family 세팅. 
	}
	
	// 답글 삽입
	@Override
	public void replyResist(Board board) {
		
		int depthCount = boardDAO.countDepth(board); // 원글과 동일한 depth의 답글의 갯수 얻어오기!!  

		if(depthCount > 1){	// 원글과 동일한 단계(depth)의 글이 있으면,
			
			// 원글과 동일 단계의 글들을 모두 rank +1 시키기. 
			int minDepthRank = boardDAO.minDepthRank(board);	// 원글과 같은 단계(depth)의 답글들 중에서 원글 바로 다음의 rank 가져오기.
			board.setRank(minDepthRank);
			boardDAO.rankUpdate(board);		// 얻어온 rank부터 모두 rank+1 시키기.
			
		}else{
			
			int dPlusOneMaxRank = boardDAO.dPlusOneMaxRank(board);	// 삽입하려는 답글과 같은 단계(원글 한단계 밑)의 답글 중에서 제일 높은 rank값 얻기.
			if(dPlusOneMaxRank != 0){	// 원글 한단계 밑 답글이 존재하면,
				board.setRank(dPlusOneMaxRank+1);
				boardDAO.rankUpdate(board);		// 얻어온 rank+1부터 모두 rank+1 시키기.
				
			}else{
				int maxRank = boardDAO.maxRank(board.getFamily());	// 원글과 같은 소속의 답글 중에 가장마지막 글의 rank값 얻기.
				if(maxRank != 0){	// 원글과 같은 소속의 답글이 존재하면,
					board.setRank(maxRank+1);	// 마지막 글 다음으로 입력.
				}
			}
						
		}
		
		boardDAO.replyInsert(board);	// 답글 삽입하기.
		
	}

	@Override
	public void update(Board board) {
		
		boardDAO.update(board);
		
	}

	@Override
	public void delete(int board_id) {
		
		boardDAO.delete(board_id);	// 게시물 삭제
		commentDAO.deleteCommentByBoard_id(board_id);	// 게시물에 딸린 댓글 삭제
		
	}
}
