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
		
		// ����¡ó��
		int blocksize = 10;
		int totalContents = boardDAO.countAll(searching);
		Pager pager = new Pager(Integer.parseInt(page), Integer.parseInt(pagesize), totalContents, blocksize);
		
		// ����Ʈ������
		Map<String, Object> paging = new HashMap<String, Object>();
		paging.put("startContent", pager.getStartContent()-1);
		paging.put("pagesize", pager.getPageSize());
		
		if(searching.getKeyword() != null){
			paging.put("searching", searching);
		}
		
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
		
		boardDAO.insert(board);	// �� ���.
		
		if(board.getBoard_id() == 0){
			throw new RegistFailException("��� ����!!");
		}
		boardDAO.setFamily(board.getBoard_id());	//	��� �� family ����. 
	}
	
	// ��� ����
	@Override
	public void replyResist(Board board) {
		
		int depthCount = boardDAO.countDepth(board); // ���۰� ������ depth�� ����� ���� ������!!  

		if(depthCount > 1){	// ���۰� ������ �ܰ�(depth)�� ���� ������,
			
			// ���۰� ���� �ܰ��� �۵��� ��� rank +1 ��Ű��. 
			int minDepthRank = boardDAO.minDepthRank(board);	// ���۰� ���� �ܰ�(depth)�� ��۵� �߿��� ���� �ٷ� ������ rank ��������.
			board.setRank(minDepthRank);
			boardDAO.rankUpdate(board);		// ���� rank���� ��� rank+1 ��Ű��.
			
		}else{
			
			int dPlusOneMaxRank = boardDAO.dPlusOneMaxRank(board);	// �����Ϸ��� ��۰� ���� �ܰ�(���� �Ѵܰ� ��)�� ��� �߿��� ���� ���� rank�� ���.
			if(dPlusOneMaxRank != 0){	// ���� �Ѵܰ� �� ����� �����ϸ�,
				board.setRank(dPlusOneMaxRank+1);
				boardDAO.rankUpdate(board);		// ���� rank+1���� ��� rank+1 ��Ű��.
				
			}else{
				int maxRank = boardDAO.maxRank(board.getFamily());	// ���۰� ���� �Ҽ��� ��� �߿� ���帶���� ���� rank�� ���.
				if(maxRank != 0){	// ���۰� ���� �Ҽ��� ����� �����ϸ�,
					board.setRank(maxRank+1);	// ������ �� �������� �Է�.
				}
			}
						
		}
		
		boardDAO.replyInsert(board);	// ��� �����ϱ�.
		
	}

	@Override
	public void update(Board board) {
		
		boardDAO.update(board);
		
	}

	@Override
	public void delete(int board_id) {
		
		boardDAO.delete(board_id);	// �Խù� ����
		commentDAO.deleteCommentByBoard_id(board_id);	// �Խù��� ���� ��� ����
		
	}
}
