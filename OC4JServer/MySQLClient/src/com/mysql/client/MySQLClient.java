package com.mysql.client;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

public class MySQLClient extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private final static Logger LOGGER = Logger.getLogger(MySQLClient.class
			.getName());
	String REST_URI;

	public void init() throws ServletException {
		REST_URI = "http://localhost:8081/MySQLService/rest/mysqlService/mysqlOperation";
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LOGGER.setLevel(Level.INFO);
		
		LOGGER.info("Class:MySQLClientt###################");

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

		
	}


}
