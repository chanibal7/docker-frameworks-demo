package com.appdynamics.webservices;

import java.sql.DriverManager;
import java.sql.ResultSet;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.activemq.ActiveMQConnectionFactory;

@Path("activeMQService")
public class ActiveMQService {
	@GET
	@Path("/activemqOperation")
	@Produces(MediaType.APPLICATION_XML)
	public String acivemqOperation() {
		System.out.println("-----------<<<<Server start>>>------------");
		try {

			thread(new HelloWorldProducer(), false);
			thread(new HelloWorldProducer(), false);
			thread(new HelloWorldConsumer(), false);
			thread(new HelloWorldConsumer(), false);
			thread(new HelloWorldProducer(), false);
			thread(new HelloWorldConsumer(), false);
			thread(new HelloWorldProducer(), false);
			thread(new HelloWorldProducer(), false);
			thread(new HelloWorldConsumer(), false);
			thread(new HelloWorldConsumer(), false);
			thread(new HelloWorldProducer(), false);
			thread(new HelloWorldConsumer(), false);
			thread(new HelloWorldProducer(), false);
			thread(new HelloWorldProducer(), false);
			thread(new HelloWorldConsumer(), false);
			thread(new HelloWorldConsumer(), false);
			thread(new HelloWorldProducer(), false);
			thread(new HelloWorldConsumer(), false);
			return "ActiveMQ test success";

		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}

	}

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/activemq";

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
		         
		         System.out.println("Name : " + name);
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
	public static void thread(Runnable runnable, boolean daemon) {
		Thread brokerThread = new Thread(runnable);
		brokerThread.setDaemon(daemon);
		brokerThread.start();
	}

	public static class HelloWorldProducer implements Runnable {
		public void run() {
			try {
				String producerName=getUserName("producer");
				System.out.println("Producer Name="+producerName);
				// Create a ConnectionFactory
				ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
						"vm://localhost");

				// Create a Connection
				Connection connection = connectionFactory.createConnection();
				connection.start();

				// Create a Session
				Session session = connection.createSession(false,
						Session.AUTO_ACKNOWLEDGE);

				// Create the destination (Topic or Queue)
				Destination destination = session.createQueue("TEST.FOO");

				// Create a MessageProducer from the Session to the Topic or
				// Queue
				MessageProducer producer = session.createProducer(destination);
				producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

				// Create a messages
				String text = "Hello world! From: "
						+ Thread.currentThread().getName() + " : "
						+ this.hashCode();
				TextMessage message = session.createTextMessage(text);

				// Tell the producer to send the message
				System.out.println("Sent message: " + message.hashCode()
						+ " : " + Thread.currentThread().getName());
				producer.send(message);

				// Clean up
				session.close();
				connection.close();
				connection = null;
			} catch (Exception e) {
				System.out.println("Caught: " + e);
				e.printStackTrace();
			}
		}
	}

	public static class HelloWorldConsumer implements Runnable,
			ExceptionListener {
		public void run() {
			try {
				String producerName=getUserName("consumer");
				System.out.println("Consumer Name="+producerName);
				// Create a ConnectionFactory
				ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
						"vm://localhost");

				// Create a Connection
				Connection connection = connectionFactory.createConnection();
				connection.start();

				connection.setExceptionListener(this);

				// Create a Session
				Session session = connection.createSession(false,
						Session.AUTO_ACKNOWLEDGE);

				// Create the destination (Topic or Queue)
				Destination destination = session.createQueue("TEST.FOO");

				// Create a MessageConsumer from the Session to the Topic or
				// Queue
				MessageConsumer consumer = session.createConsumer(destination);

				// Wait for a message
				Message message = consumer.receive(1000);

				if (message instanceof TextMessage) {
					TextMessage textMessage = (TextMessage) message;
					String text = textMessage.getText();
					System.out.println("Received: " + text);
				} else {
					System.out.println("Received: " + message);
				}

				consumer.close();
				session.close();
				connection.close();
				connection = null;
			} catch (Exception e) {
				System.out.println("Caught: " + e);
				e.printStackTrace();
			}
		}

		public synchronized void onException(JMSException ex) {
			System.out.println("JMS Exception occured.  Shutting down client.");
		}
	}
}
