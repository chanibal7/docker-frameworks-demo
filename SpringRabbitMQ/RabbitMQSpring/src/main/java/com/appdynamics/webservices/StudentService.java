package com.appdynamics.webservices;

import java.sql.DriverManager;
import java.sql.ResultSet;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.boot.SpringApplication;

import com.appdynamics.src.Application;

@Path("rabbitmq")
public class StudentService {
	@GET
	@Path("/rabbitmqOperation")
	@Produces(MediaType.APPLICATION_XML)
	public String getStudentList() {
		System.out.println("-----------<<<<Server start>>>------------");
		try {
			System.out.println("Sent by: " + getUserName("producer"));
			System.out.println("Received by: " + getUserName("consumer"));
			 SpringApplication.run(Application.class);
			 return "Success RabitMQ>>>>>";

		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/springrabbitmq";

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
