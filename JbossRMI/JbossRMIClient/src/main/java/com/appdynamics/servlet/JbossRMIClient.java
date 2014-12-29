package com.appdynamics.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JbossRMIClient extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static Logger LOGGER = Logger
			.getLogger(JbossRMIClient.class.getName());

	public JbossRMIClient() {
		super();

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		LOGGER.setLevel(Level.INFO);
		LOGGER.info("Class:JbossRMIClient###################");
		test("http://localhost:8080/JbossRMI/jbossRMI");    
		LOGGER.info("doget method #####################");
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		LOGGER.setLevel(Level.INFO);
		LOGGER.info("Class:JbossRMIClient###################");
		test("http://localhost:8080/JbossRMI/jbossRMI");    
		LOGGER.info("dopost method #####################");
	}

	public static void test(String address) {

		try {

			URL serverAddress = new URL(address);
			HttpURLConnection connection = null;
			connection = (HttpURLConnection) serverAddress.openConnection();
			connection.setRequestMethod("GET");
			connection.setDoOutput(true);
			connection.connect();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			connection.disconnect();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
