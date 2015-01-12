package com.otv.http.server;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
//import org.apache.log4j.Logger;





import com.otv.cache.service.ICacheService;
import com.otv.user.User;

/**
 * Http User Service Implementation
 * 
 * @author  onlinetechvision.com
 * @since   10 Mar 2012
 * @version 1.0.0
 *
 */
public class HttpUserService implements IHttpUserService {

	//private static Logger logger = Logger.getLogger(HttpUserService.class);
	
	//Remote Cache Service is injected...
	ICacheService cacheService;
	
	/**
	 * Add User
	 * 
	 * @param  User user
	 * @return boolean response of the method
	 */
	public boolean addUser(User user) {
		getCacheService().getUserMap().put(user.getId(), user);
		System.out.println("User has been added to cache. User : "+getCacheService().getUserMap().get(user.getId()));
		System.out.println(getUserName("appdynamics")+" >>>>>>>>>>>>>>>>>");
		return true;
	}

	/**
	 * Delete User
	 * 
	 * @param  User user
	 * @return boolean response of the method
	 */
	public boolean deleteUser(User user) {
		getCacheService().getUserMap().remove(user.getId());
		System.out.println("User has been deleted from cache. User : "+user);
		System.out.println(getUserName("appdynamics")+" >>>>>>>>>>>>>>>>>");
		return true;
	}

	/**
	 * Get User List
	 * 
	 * @return List user list
	 */
	public List<User> getUserList() {
		List<User> list = new ArrayList<User>();
		list.addAll(getCacheService().getUserMap().values());
		System.out.println("User List : "+list);
		System.out.println(getUserName("appdynamics")+" >>>>>>>>>>>>>>>>>");
		return list;
	}

	/**
	 * Get Remote Cache Service
	 * 
	 * @return ICacheService Remote Cache Service
	 */
	public ICacheService getCacheService() {
		return cacheService;
	}

	/**
	 * Set Remote Cache Service
	 * 
	 * @param ICacheService Remote Cache Service
	 */
	public void setCacheService(ICacheService cacheService) {
		this.cacheService = cacheService;
	}
	
	 static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
		static final String DB_URL = "jdbc:mysql://localhost/springintegration";

		// Database credentials
		static final String USER = "root";
		static final String PASS = "root";
		private static String getUserName(String name){
			java.sql.Connection conn = null;
			   java.sql.Statement stmt = null;
			   try{
			      //STEP 2: Register JDBC driver
			      Class.forName("com.mysql.jdbc.Driver");

			      //STEP 3: Open a connection
			      System.out.println("Connecting to database...");
			      conn = DriverManager.getConnection(DB_URL,USER,PASS);

			      //STEP 4: Execute a query
			      System.out.println("Creating statement...");
			      stmt = conn.createStatement();
			      String sql;
			      sql = "SELECT name FROM user where name='"+name+"'";
			      ResultSet rs = stmt.executeQuery(sql);
			      String username="";
			      //STEP 5: Extract data from result set
			      while(rs.next()){
			         //Retrieve by column name
			    	  username = rs.getString("name");
			         break;
			      }
			      //STEP 6: Clean-up environment
			      rs.close();
			      stmt.close();
			      conn.close();
			      return name;
			   }catch(Exception e){
			      //Handle errors for Class.forName
			      e.printStackTrace();
			   }
			   return "";
		} 

	
}
