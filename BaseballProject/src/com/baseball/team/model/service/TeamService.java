package com.baseball.team.model.service;

import java.util.List;

import com.baseball.team.model.domain.Team;

public interface TeamService {
	
	public List selectList();
	public Team selectTeam(int team_id);
	public void updateTeam(Team team);
	public void deleteTeam(int team_id);
	public void insertTeam(Team team);
	
}
