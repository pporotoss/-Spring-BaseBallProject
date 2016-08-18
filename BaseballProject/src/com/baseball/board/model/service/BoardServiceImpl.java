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
		
		// ����¡ó��
		int blocksize = 10;
		int totalContents = boardDAO.countAll();
		Pager pager = new Pager(Integer.parseInt(page), Integer.parseInt(pagesize), totalContents, blocksize);
		
		// ����Ʈ������
		Map<String, Object> paging = new HashMap<String, Object>();
		paging.put("startContent", pager.getStartContent()-1);
		paging.put("pagesize", pager.getPageSize());
		
		List list = boardDAO.selectAll(paging);
		
		// �Ѱ��� �� ���.
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
			throw new RegistFailException("��� ����!!");
		}
		boardDAO.insertKey(board.getBoard_id());
	}
	
	// ��� ����
	@Override
	public void replyResist(Board board) {
		int result = -1;
		
		// ���۰� ������ depth�� ����� ���� ������!!
		int depthCount = boardDAO.countDepth(board);  

		if(depthCount > 1){	// ���۰� ������ �ܰ��� depth�� ������,

			int minDepthRank = boardDAO.minDepthRank(board);	// ���۰� ������ depth�� �߿��� ���� �ٷ� ������ rank ��������. 
			board.setRank(minDepthRank-1);	// ���۰� ������ depth�� �߿��� ���� �ٷ� ������ rank�� �ۺ��� rank�� +1 ���ֱ� ���� rank ����.
																// �ڿ��� �����Ҷ� ���� rank +1�� ���ֱ⶧���� ������ ���� rank�� ���� ���ص� �ȴ�.
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
