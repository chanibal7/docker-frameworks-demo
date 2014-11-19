package com.solr.client;

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

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

public class SolrClient extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private final static Logger LOGGER = Logger.getLogger(SolrClient.class
			.getName());
	String REST_URI;

	public void init() throws ServletException {
		REST_URI = "http://localhost:8081/SolrService/rest/solrService/solrOperation";
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LOGGER.setLevel(Level.INFO);
		
		LOGGER.info("Class:SolrClientt###################");
		test("http://localhost:8983/solr/#/collection1/files?file=spellings.txt");
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

	public static void test(String address) {

		try {

			URL serverAddress = new URL(address);
			HttpURLConnection connection = null;
			connection = (HttpURLConnection) serverAddress.openConnection();
			connection.setRequestMethod("GET");
			connection.setDoOutput(true);
			connection.setReadTimeout(30000);
			connection.connect();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			connection.disconnect();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
