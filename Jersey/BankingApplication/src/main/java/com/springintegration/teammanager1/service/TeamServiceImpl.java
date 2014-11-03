/**
 * 
 */
package com.springintegration.teammanager1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springintegration.teammanager1.dao.TeamDAO;
import com.springintegration.teammanager1.domain.Team;

@Service("teamService")
@Transactional
public class TeamServiceImpl implements TeamService {

    @Autowired  
    private TeamDAO teamDAO;  
  
    public void addTeam(Team team) {  
    	
        teamDAO.addTeam(team);        
    }  
  
    public void updateTeam(Team team,int id) {  
    	
        teamDAO.updateTeam(team,id);  
    }  
  
    public Team getTeam(int id) {  
        return teamDAO.getTeam(id);  
    }  
  
    public void deleteTeam(int id) {  
        teamDAO.deleteTeam(id);  
    }  
  
    public List<Team> getTeams() {  
        return teamDAO.getTeams();  
    }  
	
}