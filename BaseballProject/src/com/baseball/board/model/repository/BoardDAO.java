package com.baseball.board.model.repository;

import java.util.List;
import java.util.Map;

import com.baseball.board.model.domain.Board;
import com.baseball.board.model.domain.BoardDetail;


public interface BoardDAO {
	
	public int countAll();	// ��ü �Խù� �� ��������
	public List selectAll(Map paging);
	public BoardDetail selectOne(int board_id);
	public void updateHit(int board_id);
	public int insert(Board board);
	public int update(Board board);
	public int delete(int board_id);
	public void deleteByMember(int member_id);	// ������̵�� �����ϱ�.
	public int countFamily(Board board);
	public void insertKey(int board_id);
	public int rankUpdate(Board board);	// ��ۻ��Խ� ������ ��۵� ��ũ �ϳ��� ����
	public void replyInsert(Board board);	// ��� �����ϱ�!!
	public int maxRank(int family);	// ���ۼҼ� �� �߿��� ����ū ��ũ ������!!
	public int countDepth(Board board);
	public int minDepthRank(Board board);
	public int dPlusOneMaxRank(Board board);
	
	
}
