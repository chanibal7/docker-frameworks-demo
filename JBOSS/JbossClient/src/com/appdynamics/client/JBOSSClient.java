package com.appdynamics.client;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

// Extend HttpServlet class
public class JBOSSClient extends HttpServlet {
	String REST_URI;
	String STUDENT_LIST;

	public void init() throws ServletException {
		//REST_URI = "http://localhost:8081/hibernateWebservice/rest/hibernateService/getStudentList";
		REST_URI = "http://localhost:8081/JbossSample/server/";
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			System.out.println("-----------Start Client Test2-----------");
			// Set response content type
			HttpClient client = new DefaultHttpClient();
		    HttpPost post = new HttpPost(REST_URI);
		 
			// add request header
		    HttpResponse httpresponse = client.execute(post);
			System.out.println("httpresponse=="+httpresponse);
			//response.sendRedirect(REST_URI);
			System.out.println("-----------------Client End2----------------");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}