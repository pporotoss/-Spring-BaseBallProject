package com.baseball.team.model.repository;

import java.util.List;

import com.baseball.team.model.domain.Team;

public interface TeamDAO {
	
	public List selectList();
	public Team selectTeam(int team_id);
	public int updateTeam(Team team);
	public int deleteTeam(int team_id);
	public int insertTeam(Team team);
	
}
