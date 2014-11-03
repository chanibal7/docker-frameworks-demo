package com.sample.restfull;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.sample.pojo.Track;
import com.sample.pojo.User;


@Path("/collectinginfo")
public class DbInfoServices {
	private final static Logger LOGGER = Logger.getLogger(DbInfoServices.class.getName()); 
	private Connection connection;
	@GET
	@Path("/{param}")
	public void getMsg(@PathParam("param") String msg) {
		 LOGGER.setLevel(Level.INFO);

 
		String output = "Jersey say : " + msg;
		LOGGER.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+output);
 
		
 
	}
	
	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)
	public Track getTrackInJSON() {
 
		Track track = new Track();
		track.setTitle("Enter Sandman");
		track.setSinger("Metallica");
		PreparedStatement ps = null;
        ResultSet rs = null;
        String email="anil@neevtech.com";
        String password="123456";
		LOGGER.info("Class:DbInfoServices###################");
		
		
		
		  try {
			Class.forName("com.mysql.jdbc.Driver");
			
		    connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "root");
		    if(connection!=null){
		    ps = connection.prepareStatement("select id, name, email,country from Users where email=? and password=? limit 1");
            ps.setString(1, email);
            ps.setString(2, password);
            rs = ps.executeQuery();
            if(rs != null && rs.next()){
                
                User user = new User(rs.getString("name"), rs.getString("email"), rs.getString("country"), rs.getInt("id"));
                LOGGER.info("User found with details="+user);
                connection.close();
                
		  }
		    }
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
		LOGGER.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>ObjectSaved>>>>>>>>>"+track);
		 return track;
 
	}

}
