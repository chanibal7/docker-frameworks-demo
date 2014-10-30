package com.demo;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class CallingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static Logger LOGGER = Logger.getLogger(CallingServlet.class .getName()); 
	private Connection connection;
       
    
    public CallingServlet() {
        super();
        
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOGGER.setLevel(Level.INFO); 
		PreparedStatement ps = null;
        ResultSet rs = null;
        String email="xyz@gmail.com";
        String pwd="dynamics";
		LOGGER.info("Class:CallingServlet###################");
		try {
			Class.forName("com.mysql.jdbc.Driver");			
		    connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "root");
		    if(connection!=null){
		    ps = connection.prepareStatement("select id, name, email,country from Users where email=? and pwd=? limit 1");
            ps.setString(1, email);
            ps.setString(2, pwd);
            rs = ps.executeQuery();
            
            if(rs != null && rs.next()){
                
                User user = new User(rs.getString("name"), rs.getString("email"), rs.getString("country"), rs.getInt("id"));
                System.out.println("User found with details="+user);
                
		  }
            connection.close();
		    }
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		LOGGER.info("doget method #####################");
	
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOGGER.setLevel(Level.INFO); 
		LOGGER.info("Class:CallingServlet###################");
		LOGGER.info("dopost method #####################");
	}
	
	
	

}
