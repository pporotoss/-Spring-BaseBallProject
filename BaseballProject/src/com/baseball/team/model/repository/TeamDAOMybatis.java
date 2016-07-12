package com.baseball.team.model.repository;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.baseball.team.model.domain.Team;

@Repository
public class TeamDAOMybatis implements TeamDAO{

	@Autowired
	SqlSessionTemplate sqlSessionTemplate;
	
	@Override
	public List selectList() {
		
		List list = sqlSessionTemplate.selectList("Team.selectAll");
		
		return list;
	}

	@Override
	public Team selectTeam(int team_id) {
		
		Team team = sqlSessionTemplate.selectOne("Team.selectOnt", team_id);
		
		return team;
	}

	@Override
	public int updateTeam(Team team) {
		
		int result = sqlSessionTemplate.update("Team.update", team);
		
		return result;
	}

	@Override
	public int deleteTeam(int team_id) {

		int result = sqlSessionTemplate.delete("Team.delete", team_id);
		
		return result;
	}

	@Override
	public int insertTeam(Team team) {
		
		int result = sqlSessionTemplate.insert("Team.insert", team);
		
		return result;
	}

}
