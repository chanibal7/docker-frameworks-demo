package com.otv.http.client;


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

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.otv.http.server.IHttpUserService;
import com.otv.user.User;

public class HttpUserServiceClient extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static Logger LOGGER = Logger
			.getLogger(HttpUserServiceClient.class.getName());

	public HttpUserServiceClient() {
		super();

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		LOGGER.setLevel(Level.INFO);
		LOGGER.info("Class:HttpUserServiceClient###################");
		remoteCall();   
		LOGGER.info("doget method #####################");
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		LOGGER.setLevel(Level.INFO);
		LOGGER.info("Class:HttpUserServiceClient###################");
		remoteCall(); 
		LOGGER.info("dopost method #####################");
	}

public void remoteCall(){
		
		
		//Http Client Application Context is started... 
		ApplicationContext context = new ClassPathXmlApplicationContext("httpClientAppContext.xml");
		
		//Remote User Service is called via Http Client Application Context...
		IHttpUserService httpClient = (IHttpUserService) context.getBean("HttpUserService");

		//New User is created...
		User user = new User();
		user.setId(1);
		user.setName("Bruce");
		user.setSurname("Willis");
		
		//The user is added to the remote cache...
		System.out.println("httpClient.addUser>>>>>>>");
		httpClient.addUser(user);
		System.out.println("httpClient.getUserList>>>>>>>");
		//The users are gotten via remote cache...
		httpClient.getUserList();
		System.out.println("httpClient.deleteUser>>>>>>>");
		//The user is deleted from remote cache...
		httpClient.deleteUser(user);
		
		//The users are gotten via remote cache...
		httpClient.getUserList();
		
}

}




















