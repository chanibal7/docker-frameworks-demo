package com.sample.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.Certificate;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WebsphereClient extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static Logger LOGGER = Logger.getLogger(WebsphereClient.class
			.getName());

	public WebsphereClient() {
		super();

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		LOGGER.setLevel(Level.INFO);
		LOGGER.info("Class:WebsphereClient###################");
		testHttpURL("http://localhost:9080/hitcount");
		getUserName();
		LOGGER.info("doget WebsphereClient #####################");
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		LOGGER.setLevel(Level.INFO);
		LOGGER.info("Class:Javaemail###################");
		testHttpURL("http://localhost:9080/hitcount");
		getUserName();
		LOGGER.info("dopost method #####################");
	}
	
	/*private static void testWget(){
		try
		{   testHttpURL("http://localhost:9080/hitcount");
			Process p = Runtime.getRuntime().exec("wget --no-proxy --no-check-certificate https://localhost:9043/admin");
			BufferedReader s= new BufferedReader(new InputStreamReader(p.getInputStream()));
		}catch(IOException ioe)
		{
		
		}
	}*/
	private static void testHttpURL(String httpURL){
		try {
			URL serverAddress = new URL(httpURL);
			HttpURLConnection connection = null;
			connection = (HttpURLConnection) serverAddress.openConnection();
			connection.setRequestMethod("GET");
			connection.setDoOutput(true);
			connection.connect();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			connection.disconnect();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/jbossdb";

	// Database credentials
	static final String USER = "root";
	static final String PASS = "root";

	public static String getUserName() {
		java.sql.Connection conn = null;
		java.sql.Statement stmt = null;
		try {
			// STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// STEP 4: Execute a query
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT name FROM user";
			ResultSet rs = stmt.executeQuery(sql);
			String name = "";
			// STEP 5: Extract data from result set
			while (rs.next()) {
				// Retrieve by column name
				name = rs.getString("name");

				System.out.println("Name : " + name);
			}
			// STEP 6: Clean-up environment
			rs.close();
			stmt.close();
			conn.close();
			return name;
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		}
		return "";
	}

}
