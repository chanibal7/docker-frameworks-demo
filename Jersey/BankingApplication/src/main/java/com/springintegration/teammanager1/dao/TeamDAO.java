package com.springintegration.teammanager1.dao;

import java.util.List;

import com.springintegration.teammanager1.domain.Team;

public interface TeamDAO {

    public void addTeam(Team team);  
    public void updateTeam(Team team,int id);  
    public Team getTeam(int id);  
    public void deleteTeam(int id);  
    public List<Team> getTeams();  

}