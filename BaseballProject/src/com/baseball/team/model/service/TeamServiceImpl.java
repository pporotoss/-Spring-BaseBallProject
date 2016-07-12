package com.baseball.team.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baseball.team.model.domain.Team;
import com.baseball.team.model.repository.TeamDAO;

@Service
public class TeamServiceImpl implements TeamService{

	@Autowired
	private TeamDAO teamDAO; 
	
	@Override
	public List selectList() {

		
		return teamDAO.selectList();
	}

	@Override
	public Team selectTeam(int team_id) {
		return teamDAO.selectTeam(team_id);
	}

	@Override
	public void updateTeam(Team team) {
		teamDAO.updateTeam(team);
	}

	@Override
	public void deleteTeam(int team_id) {
		teamDAO.deleteTeam(team_id);
	}

	@Override
	public void insertTeam(Team team) {
		// TODO Auto-generated method stub
		
	}

}
