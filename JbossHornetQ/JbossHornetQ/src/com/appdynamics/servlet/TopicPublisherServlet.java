package com.appdynamics.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.jms.*;
import javax.naming.InitialContext;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Date;

/**
 * User: jayesh
 * Date: May 13, 2009
 */
public class TopicPublisherServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	getUserName();
    	process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) {
        try {
            InitialContext ctx = new InitialContext();
            TopicConnectionFactory topicConnectionFactory = (TopicConnectionFactory) ctx.lookup((String)request.getParameter("connectionfactory"));
            TopicConnection tConnection = topicConnectionFactory.createTopicConnection();
            TopicSession topicSession = tConnection.createTopicSession(false, TopicSession.AUTO_ACKNOWLEDGE);
            TopicPublisher topicPublisher = topicSession.createPublisher(((Topic) ctx.lookup((String)request.getParameter("destination"))));
            System.out.println("Sending messages ");
            TextMessage textMsg = topicSession.createTextMessage();
            for (int i = 1; i <= 10; i++) {
                textMsg.clearBody();
                textMsg.setIntProperty("severity", i);
                textMsg.setText("Message #" + i);
                System.out.println(" Sending message: Message #" + i);
                topicPublisher.send(textMsg);
            }
            textMsg.clearBody();
            textMsg.setIntProperty("severity", 0);
            textMsg.setText("Stop");
            System.out.println(" Sending message: Stop");
            topicPublisher.send(textMsg);
            System.out.println("Messages Sent Successfully");
            response.getWriter().println("Message Sent successfully to Topic at "+new Date());
            topicPublisher.close();
            topicSession.close();
            tConnection.close();
        } catch (Exception e) {
            e.printStackTrace();
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