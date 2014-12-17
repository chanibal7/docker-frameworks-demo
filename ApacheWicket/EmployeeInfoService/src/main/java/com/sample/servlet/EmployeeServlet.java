package com.sample.servlet;

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
import javax.servlet.http.HttpSession;

import com.sample.domain.User;

/**
 * Servlet implementation class MyServlet
 */
public class EmployeeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static Logger LOGGER = Logger.getLogger(EmployeeServlet.class .getName()); 
	private Connection connection;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		LOGGER.setLevel(Level.INFO); 
		PreparedStatement ps = null;
        ResultSet rs = null;
        String email="anil@neevtech.com";
        String password="123456";
		LOGGER.info("Class:EmployeeServlet###################");
		
		
		
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
		
		
		LOGGER.info("doget method #####################");

		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO 
		LOGGER.setLevel(Level.INFO); 
		LOGGER.info("Class:EmployeeServlet###################");
		LOGGER.info("dopost method #####################");
		
	}

}
