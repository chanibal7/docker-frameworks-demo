package com.appdynamics.webservices;

import java.sql.DriverManager;
import java.sql.ResultSet;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.TextMessage;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.tibco.tibjms.TibjmsQueueConnectionFactory;

@Path("tibcoEmsService")
public class TibcoEMS {
	@GET
	@Path("/tibcoEms")
	@Produces(MediaType.APPLICATION_XML)
	public String testTibcoEMS() {
		String serverUrl = "tcp://localhost:7222";
		String userName = "";
		String password = "";

		String queueName = "queue.Sample";

		try {

			System.out.println("Start listening for incoming JMS message on "
					+ serverUrl + "...");

			QueueConnectionFactory factory = new TibjmsQueueConnectionFactory(
					serverUrl);
			QueueConnection connection = factory.createQueueConnection(
					userName, password);
			QueueSession session = connection.createQueueSession(false,
					javax.jms.Session.AUTO_ACKNOWLEDGE);

			// Use createQueue() to enable receiving from dynamic queues.
			Queue receiverQueue = session.createQueue(queueName);
			QueueReceiver receiver = session.createReceiver(receiverQueue);
			QueueSender sender = session.createSender(receiverQueue);
			connection.start();

			TextMessage jmsMessage = session.createTextMessage();
			String text = "Hello....";
			jmsMessage.setText(text);
			sender.send(jmsMessage);
			
			System.out.println("Sent by: " + getUserName("producer"));
			System.out.println("Sent message: " + text);
			/* read queue messages */
			// while (true) {
			TextMessage message = (TextMessage) receiver.receive();
			System.out.println("Received by: " + getUserName("consumer"));
			System.out.println("Received message: " + message.getText());
			// }

			connection.close();

		} catch (JMSException e) {
			e.printStackTrace();
			return "Failure";
		}
		return "Success";
	}
	
  	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/tibcoems";

	// Database credentials
	static final String USER = "root";
	static final String PASS = "root";
	public static String getUserName(String type){
		java.sql.Connection conn = null;
		   java.sql.Statement stmt = null;
		   try{
		      //STEP 2: Register JDBC driver
		      Class.forName("com.mysql.jdbc.Driver");

		      //STEP 3: Open a connection
		      System.out.println("Connecting to database...");
		      conn = DriverManager.getConnection(DB_URL,USER,PASS);

		      //STEP 4: Execute a query
		      System.out.println("Creating statement...");
		      stmt = conn.createStatement();
		      String sql;
		      sql = "SELECT name FROM user where type='"+type+"'";
		      ResultSet rs = stmt.executeQuery(sql);
		      String name="";
		      //STEP 5: Extract data from result set
		      while(rs.next()){
		         //Retrieve by column name
		         name = rs.getString("name");
		         break;
		      }
		      //STEP 6: Clean-up environment
		      rs.close();
		      stmt.close();
		      conn.close();
		      return name;
		   }catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
		   }
		   return "";
	}

}