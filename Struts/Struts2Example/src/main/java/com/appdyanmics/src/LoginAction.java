package com.appdyanmics.src;
 
import java.sql.DriverManager;
import java.sql.ResultSet;

import com.opensymphony.xwork2.Action;
 
public class LoginAction implements Action {
     
    @Override
    public String execute() throws Exception {
    	System.out.println("Login susuccess for user="+getUserName("appdynamics"));
        if(!getUserName("appdynamics").equals(""))
        return "SUCCESS";
        else return "ERROR";
    }
     
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/struts";

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
    //Java Bean to hold the form parameters
    private String name;
    private String pwd;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPwd() {
        return pwd;
    }
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
     
}