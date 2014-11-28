package com.appdynamics.servlet;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Hashtable;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
 
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class SimpleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SimpleServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("SimpleServlet doGet>>>>>>>>>>>>>>>>>");
		getUserName();
		try {
			jbossHornetQOperation(request);
		} catch (Exception e) {
			e.printStackTrace();// TODO: handle exception
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("SimpleServlet doPost>>>>>>>>>>>>>>>>>");
		getUserName();// TODO Auto-generated method stub
		try {
			jbossHornetQOperation(request);
		} catch (Exception e) {
			e.printStackTrace();// TODO: handle exception
		}
	}

	private final static String JNDI_FACTORY = "org.jboss.naming.remote.client.InitialContextFactory";
	private final static String JMS_FACTORY = "jms/RemoteConnectionFactory";
	//private final static String JMS_FACTORY = "/ConnectionFactory";
	private final static String QUEUE = "jms/queue/test";
	private final static String jbossUrl = "remote://localhost:4447";

	private static String getPropertyVal(HttpServletRequest request, String propertyName, String defaultValue){
		String property = request.getParameter(propertyName)==null?defaultValue : request.getParameter(propertyName);
		System.out.println(String.format("PROPERTY RETURNED for %s IS %s ", propertyName, property ));
		return property;
		
		
	}
	private static InitialContext getInitialContext(HttpServletRequest request) throws NamingException {
		Hashtable env = new Hashtable();
		System.out.println("getInitialContext()>>>>>>>>>>>>>>>>");
		env.put(Context.INITIAL_CONTEXT_FACTORY, getPropertyVal(request, "jndifactory", JNDI_FACTORY));
		env.put(Context.PROVIDER_URL, jbossUrl);
		/*env.put(Context.SECURITY_PRINCIPAL, "testu");
		env.put(Context.SECURITY_CREDENTIALS, "testp");*/
		env.put(Context.SECURITY_PRINCIPAL, getPropertyVal(request, "user", "testuser"));
		env.put(Context.SECURITY_CREDENTIALS, getPropertyVal(request, "password", "testpassword"));
		return new InitialContext(env);
	}
	private void jbossHornetQOperation(HttpServletRequest request) throws Exception{
		InitialContext ic = getInitialContext(request);
		System.out.println("ic1>>>>>>>>>>");
		System.out.println(ic);
		System.out.println("ic2>>>>>>>>>>");
		
		QueueConnectionFactory qconFactory = (QueueConnectionFactory) ic
				.lookup(getPropertyVal(request, "jmsfactory", JMS_FACTORY));
		System.out.println("11111===>>>>>>>>>>" +(qconFactory==null));
		QueueConnection qcon = qconFactory.createQueueConnection(getPropertyVal(request, "user", "testuser"),getPropertyVal(request, "password", "testpassword"));
//		ConnectionFactory cf = (ConnectionFactory) context.lookup("jms/RemoteConnectionFactory");

//	        Destination destination = (Destination) context.lookup("jms/
		QueueSession qsession = qcon.createQueueSession(false,
				Session.AUTO_ACKNOWLEDGE);
		System.out.println("1111122>>>>>>>>>>");
		System.out.println("1111133>>>>>>>>>>");
		Queue queue = (Queue) ic.lookup(getPropertyVal(request, "queue", QUEUE));
		QueueSender qsender = qsession.createSender(queue);

		qcon.start();

		TextMessage msg = qsession.createTextMessage();
		msg.setText("HelloWorld");
		qsender.send(msg);
		
		
		 //QueueReceiver qreceiver = qsession.createReceiver(queue);
		 
		 //qcon.start();
		  
		 /*msg = (TextMessage)qreceiver.receive();
		 System.out.println(msg.getText());*/
		  
		qsender.close();
		qsession.close();
		qcon.close();
	}

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/jbossdb";

	// Database credentials
	static final String USER = "root";
	static final String PASS = "neev";

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
