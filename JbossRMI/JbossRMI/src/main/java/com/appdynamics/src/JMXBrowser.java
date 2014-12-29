package com.appdynamics.src;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.management.MBeanInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanParameterInfo;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JMXBrowser extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static Logger LOGGER = Logger
			.getLogger(JMXBrowser.class.getName());

	public JMXBrowser() {
		super();

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		LOGGER.setLevel(Level.INFO);
		LOGGER.info("Class:JbossRMI###################");
		try {
			getUserName();
			callrmi();
		} catch (Exception e) {
		}
		LOGGER.info("doget method #####################");
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		LOGGER.setLevel(Level.INFO);
		LOGGER.info("Class:JbossRMI###################");
		try {
			getUserName();
			callrmi();
		} catch (Exception e) {
		}
		LOGGER.info("dopost method #####################");
	}

	public static void callrmi() throws Exception{
		Properties env = new Properties();  
		 env.setProperty(Context.INITIAL_CONTEXT_FACTORY,  
		 "org.jboss.naming.HttpNamingContextFactory");  
		  
		 env.setProperty(Context.PROVIDER_URL,  
		 "http://localhost:8080/invoker/JNDIFactory");  
		  
		 Context ctx = new InitialContext(env);  
		  
		 MBeanServerConnection server = (MBeanServerConnection) ctx.lookup("jmx/invoker/RMIAdaptor");
	      // RMIAdaptor server = (RMIAdaptor) ic.lookup("jmx/invoker/RMIAdaptor");
	        
	        // Get the MBeanInfo for the JNDIView MBean
	        ObjectName name = new ObjectName("jboss:service=JNDIView");
	        MBeanInfo  info = server.getMBeanInfo(name);
	        System.out.println("JNDIView Class: " + info.getClassName());

	        MBeanOperationInfo[] opInfo = info.getOperations();
	        System.out.println("JNDIView Operations: ");
	        for(int o = 0; o < opInfo.length; o ++) {
	            MBeanOperationInfo op = opInfo[o];

	            String returnType = op.getReturnType();
	            String opName     = op.getName();
	            System.out.print(" + " + returnType + " " + opName + "(");

	            MBeanParameterInfo[] params = op.getSignature();
	            for(int p = 0; p < params.length; p++)  {
	                MBeanParameterInfo paramInfo = params[p];

	                String pname = paramInfo.getName();
	                String type  = paramInfo.getType();

	                if (pname.equals(type)) {
	                    System.out.print(type);
	                } else {
	                    System.out.print(type + " " + name);
	                }

	                if (p < params.length-1) {
	                    System.out.print(','); 
	                }
	            }
	            System.out.println(")");
	        }
	        
	        // Invoke the list(boolean) op
	        String[] sig    = {"boolean"};
	        Object[] opArgs = {Boolean.TRUE};
	        Object   result = server.invoke(name, "list", opArgs, sig);

	        System.out.println("JNDIView.list(true) output:\n"+result);
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

