package com.baseball.board.model.repository;

import java.util.List;
import java.util.Map;

import com.baseball.board.model.domain.Board;
import com.baseball.board.model.domain.BoardDetail;

import common.Searching;


public interface BoardDAO {
	
	public int countAll(Searching searching);	// 전체 게시물 수 가져오기
	public List selectAll(Map paging);
	public BoardDetail selectOne(int board_id);
	public void updateHit(int board_id);
	public int insert(Board board);
	public int update(Board board);
	public int delete(int board_id);
	public void deleteByMember(int member_id);	// 멤버아이디로 삭제하기.
	public int countFamily(Board board);
	public void setFamily(int board_id);
	public int rankUpdate(Board board);	// 답글삽입시 나머지 답글들 랭크 하나씩 증가
	public void replyInsert(Board board);	// 답글 삽입하기!!
	public int maxRank(int family);	// 원글소속 글 중에서 가장큰 랭크 얻어오기!!
	public int countDepth(Board board);
	public int minDepthRank(Board board);
	public int dPlusOneMaxRank(Board board);
	public List userContents(Map user);	// 사용자가 쓴 글 모두 가져오기.
	public int userContentsCount(int member_id);	// 해당 사용자가 쓴 글의 갯수 가져오기.
	
}
