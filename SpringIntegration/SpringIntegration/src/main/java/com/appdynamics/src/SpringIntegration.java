package com.appdynamics.src;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.PollableChannel;
import org.springframework.messaging.support.GenericMessage;

public class SpringIntegration extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static Logger LOGGER = Logger
			.getLogger(SpringIntegration.class.getName());

	public SpringIntegration() {
		super();

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		callSpringIntegrationSample();
		
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		LOGGER.setLevel(Level.INFO);
		LOGGER.info("Class:StrutsClientServlet###################");
		callSpringIntegrationSample();   
		LOGGER.info("dopost method #####################");
	}
	
	private void callSpringIntegrationSample(){
		AbstractApplicationContext context = new ClassPathXmlApplicationContext(
				"/META-INF/spring/integration/helloWorldDemo.xml",
				HelloWorldApp.class);
		MessageChannel inputChannel = context.getBean("inputChannel",
				MessageChannel.class);
		PollableChannel outputChannel = context.getBean("outputChannel",
				PollableChannel.class);
		inputChannel.send(new GenericMessage<String>("World"));
		System.out.println("HelloWorldDemo:: "
				+ outputChannel.receive(0).getPayload());
		System.out.println(getUserName("appdynamics")+" >>>>>>>>>>>>>>>>>");
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
