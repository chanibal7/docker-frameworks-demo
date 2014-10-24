package com.appdynamics;

import java.sql.DriverManager;
import java.sql.ResultSet;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Session Bean implementation class HelloWorld
 */
@Stateless
@LocalBean
public class HelloWorld implements HelloWorldLocal {

    /**
     * Default constructor. 
     */
    public HelloWorld() {
        // TODO Auto-generated constructor stub
    }
    public String sayHello(){
    	String producer=getUserName("producer");
    	String consumer=getUserName("consumer");
    	return "Producer : "+producer+" <........> Consumer : "+consumer;
    }
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/ejbdb";

	// Database credentials
	static final String USER = "root";
	static final String PASS = "root";
	public static String getUserName(String type){
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
		      sql = "SELECT name FROM user where type='"+type+"'";
		      ResultSet rs = stmt.executeQuery(sql);
		      String name="";
		      //STEP 5: Extract data from result set
		      while(rs.next()){
		         //Retrieve by column name
		         name = rs.getString("name");
		         
		         System.out.println("Name : " + name);
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
