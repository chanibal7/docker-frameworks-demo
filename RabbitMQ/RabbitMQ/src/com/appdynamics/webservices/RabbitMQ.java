package com.appdynamics.webservices;

import java.sql.DriverManager;
import java.sql.ResultSet;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;
@Path("rabbitMQService")
public class RabbitMQ {
    private final static String QUEUE_NAME = "hello";
  	@GET
  	@Path("/rabbitmqOperation")
  	@Produces(MediaType.APPLICATION_XML)
  	public String rabbitmqOperation() {
  		System.out.println("-----------<<<<Server start>>>------------");
  		try {

  			ConnectionFactory factory = new ConnectionFactory();
  		    factory.setHost("localhost");
  		    Connection connection = factory.newConnection();
  		    Channel channel = connection.createChannel();
  		    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
  		    
  		    String message = "Hello World!";
  		    channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
	  		String producerName=getUserName("producer");
	  		System.out.println(" Producer '" + producerName + "'");
  		    System.out.println(" [x] Sent '" + message + "'");
  		    
		    QueueingConsumer consumer = new QueueingConsumer(channel);
		    channel.basicConsume(QUEUE_NAME, true, consumer);
		    QueueingConsumer.Delivery delivery = consumer.nextDelivery();
		    message = new String(delivery.getBody());
		    String consumerName=getUserName("consumer");
	  		System.out.println(" Consumer '" + consumerName + "'");
		    System.out.println(" [x] Received '" + message + "'");
  		   
  		    channel.close();
  		    connection.close();
  			return "RabbitMQ test success!";

  		} catch (Exception e) {
  			e.printStackTrace();
  			return "RabbitMQ test failure!";
  		}

  	}
  	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/rabbitmq";

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
  }
  
