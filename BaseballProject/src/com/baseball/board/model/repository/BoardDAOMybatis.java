package com.baseball.board.model.repository;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.baseball.board.model.domain.Board;
import com.baseball.board.model.domain.BoardDetail;

import common.Searching;

@Repository
public class  BoardDAOMybatis implements BoardDAO{
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	@Override
	public int countAll(Searching searching) {
		
		return sqlSessionTemplate.selectOne("Board.countAll", searching);
	}
	
	@Override
	public List selectAll(Map paging) {
		
		return sqlSessionTemplate.selectList("Board.selectAll", paging);
	}

	@Override
	public BoardDetail selectOne(int board_id) {

		return sqlSessionTemplate.selectOne("Board.selectOne", board_id);
	}

	@Override
	public void updateHit(int board_id) {
		sqlSessionTemplate.update("Board.updateHit", board_id);
	}
	
	@Override
	public int insert(Board board) {
		
		int result = sqlSessionTemplate.insert("Board.insert", board);
		return 0;
	}

	@Override
	public int update(Board board) {
		
		sqlSessionTemplate.update("Board.update", board);
		
		return 0;
	}

	@Override
	public int delete(int board_id) {
		
		sqlSessionTemplate.delete("Board.delete", board_id);
		
		return 0;
	}
	
	@Override
	public void deleteByMember(int member_id) {
		
		sqlSessionTemplate.delete("Board.deleteByMember", member_id);
		
	}

	@Override
	public int countFamily(Board board) {
		
		return sqlSessionTemplate.selectOne("Board.countFamily", board);
	}

	@Override
	public void insertKey(int board_id) {
		
		sqlSessionTemplate.update("Board.insertKey", board_id);
	}

	@Override
	public int rankUpdate(Board board) {
		
		return sqlSessionTemplate.update("Board.rankUpdate", board);
	}

	@Override
	public void replyInsert(Board board) {
		
		sqlSessionTemplate.insert("Board.replyInsert", board);		
	}
	
	@Override
	public int maxRank(int family) {
		
		return sqlSessionTemplate.selectOne("Board.maxRank", family);
	}
	
	@Override
	public int countDepth(Board board) {
		
		return sqlSessionTemplate.selectOne("Board.countDepth", board);
	}
	
	@Override
	public int minDepthRank(Board board) {
		
		int result = sqlSessionTemplate.selectOne("Board.minDepthRank", board);
		
		return result;
	}

	@Override
	public int dPlusOneMaxRank(Board board) {
		
		int result = sqlSessionTemplate.selectOne("Board.dPlusOneMaxRank", board);
		
		return result;
	}

	@Override
	public List userContents(Map user) {
		
		return sqlSessionTemplate.selectList("Board.userContents", user);
	}

	@Override
	public int userContentsCount(int member_id) {

		return sqlSessionTemplate.selectOne("Board.userContentsCount", member_id);
	}
}
