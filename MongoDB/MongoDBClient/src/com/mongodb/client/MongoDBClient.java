package com.mongodb.client;

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

import com.mongodb.domain.User;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

// Extend HttpServlet class
public class MongoDBClient extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static Logger LOGGER = Logger.getLogger(MongoDBClient.class
			.getName());
	String REST_URI;
	private Connection connection;

	public void init() throws ServletException {
		REST_URI = "http://localhost:8081/MongoDBService/rest/mongoDBService/mongoDBOperation";
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LOGGER.setLevel(Level.INFO);
		PreparedStatement ps = null;
		ResultSet rs = null;
		String name = "Jack";
		
		LOGGER.info("Class:MongoDBClientt###################");

		try {
			System.out
					.println("**************************** Start Client Test *****************************");
			// Set response content type
			Client c = Client.create();
			WebResource resource = c.resource(REST_URI);
			String content = resource.get(String.class);
			System.out
					.println("***************************** End Client Test ******************************");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			Thread.sleep(2000);
			LOGGER.info(" ############## Start SQL Operation  ###################");

			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/test", "root", "root");
			if (connection != null) {
				ps = connection
						.prepareStatement("select id, name, email,country from Users where name=? limit 1");
				ps.setString(1, name);
				rs = ps.executeQuery();
				if (rs != null && rs.next()) {

					User user = new User(rs.getString("name"),
							rs.getString("email"), rs.getString("country"),
							rs.getInt("id"));
					LOGGER.info("User found with details=" + user);
					connection.close();
					LOGGER.info(" ############## End SQL Operation  ###################");
				}
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}