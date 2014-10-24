package com.appdynamics.client;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

// Extend HttpServlet class
public class ActiveMQClient extends HttpServlet {
	String REST_URI;
	public void init() throws ServletException {
		REST_URI = "http://localhost:8081/activemq/rest/activeMQService/activemqOperation";
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			System.out.println("-----------Start Client Test-----------");
			Client c = Client.create();
			WebResource resource = c.resource(REST_URI);
			String content = resource.get(String.class);
			System.out.println("Total Size : " + content);
			System.out.println("-----------------Client End----------------");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}