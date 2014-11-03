package com.springintegration.teammanager1.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.springintegration.teammanager1.domain.Team;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
//import net.viralpatel.spring3.form.Contact;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Repository 
public class TeamDAOImpl implements TeamDAO {

    @Autowired  
    private SessionFactory sess;  
  

  

	
  
    public void addTeam(Team team) { 
    	/*Invoking jersey services	*/
    	try {
    		Client client = Client.create();
    		WebResource webResource = client
    		   .resource("http://localhost:8081/JerseyServices/rest/collectinginfo/get");
    		ClientResponse response = webResource.accept("application/json")
                       .get(ClientResponse.class);
    		if (response.getStatus() != 200) {
    		   throw new RuntimeException("Failed : HTTP error code : "
    			+ response.getStatus());
    		}
    		String output = response.getEntity(String.class);
    	  } catch (Exception e) {
    		e.printStackTrace();
    	  }
    /*ending jersey services	*/
   	Session session = null;
	session = sess.openSession();
	Transaction trans = session.beginTransaction();
	session.save(team);
	trans.commit();
	session.close();
    	    }  
  
    public void updateTeam(Team team,int id) {  
    	Session session=null;
    	session=sess.openSession();
    	Transaction trans=session.beginTransaction();
    	Team teamtoupdate=(Team)session.get(Team.class, id);
    	teamtoupdate.setName(team.getName());
    	teamtoupdate.setRating(team.getRating());
        session.saveOrUpdate(teamtoupdate);  
        trans.commit();
        session.close();
            }  
  
    public Team getTeam(int id) {  
    	Session session = null;
		session = sess.openSession();
		Transaction t = session.beginTransaction();
        Team team = (Team)session.get(Team.class, id);
        t.commit();
		session.close();
        if (null != team) 
        	return team;
        return team;  
    }  
  
    public void deleteTeam(int id) {
    	Session session=null;
    	session=sess.openSession();
    	Transaction trans=session.beginTransaction();
    	Team team=(Team)session.get(Team.class, id);
        if (team != null)  
            session.delete(team);  
        trans.commit();
        session.close();
    }  
  
    @SuppressWarnings("unchecked")  
    public List<Team> getTeams() {
    	Session session=null;
    	session=sess.openSession();
    	Transaction trans=session.beginTransaction();
    	List<Team>list=session.createQuery("from Team").list();
        return list;  
    }  
  

}