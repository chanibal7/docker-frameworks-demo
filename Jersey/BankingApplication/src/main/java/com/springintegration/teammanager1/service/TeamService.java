package com.springintegration.teammanager1.service;

import java.util.List;

import com.springintegration.teammanager1.domain.Team;

public interface TeamService {
	public void addTeam(Team team);

	public void updateTeam(Team team,int id);

	public Team getTeam(int id);

	public void deleteTeam(int id);

	public List<Team> getTeams();

}