package com.appdynamics.src;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.cache.PropertyConfigurator;
import org.jboss.cache.TreeCache;
import org.jboss.cache.aop.TreeCacheAop;

public class JbossTree extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static Logger LOGGER = Logger
			.getLogger(JbossTree.class.getName());

	public JbossTree() {
		super();

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		LOGGER.setLevel(Level.INFO);
		LOGGER.info("Class:JbossTree###################");
		try {
			getUserName();
			TreeCache tree = new TreeCache(); 
			PropertyConfigurator config = new PropertyConfigurator(); // configure tree cache. Needs to be in the classpath 
			config.configure(tree, "META-INF/replSync-service.xml"); 
			tree.startService(); // kick start tree cache
			tree.put("/a/b/c", "ben", "me"); // create a cache entry. 
			// Node "/a/b/c" will be created if not yet existed.
			tree.put("/a/b/c/d", "JBoss", "Open Source");
			String cacheData=(String)tree.get("/a/b/c/d", "JBoss");
			System.out.println("CacheData="+cacheData);
			TreeCacheAop treeAop = new TreeCacheAop();
			Person joe = new Person(); // instantiate a Person object named joe 
			joe.setName("Joe Black"); 
			joe.setAge(31); 
			Address addr = new Address(); // instantiate a Address object named addr 
			addr.setCity("Sunnyvale"); 
			addr.setStreet("123 Albert Ave"); 
			addr.setZip("94086"); 
			joe.setAddress(addr); // set the address reference 
			treeAop.startService(); // kick start tree cache
			treeAop.putObject("/aop/joe", joe); // add aop sanctioned object (and sub-objects) into cache. 
			System.out.println(treeAop.getObject("/aop/joe")+">>>>>>>>>>>>>>>>>>>");
			// since it is aop-sanctioned, use of plain get/set methods will take care of cache contents automatically. 
			joe.setAge(41);
			getUserName();
		} catch (Exception e) {
			// TODO: handle exception
		}
		LOGGER.info("doget method #####################");
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		LOGGER.setLevel(Level.INFO);
		LOGGER.info("Class:JbossTree###################");
		try {
			getUserName();
		} catch (Exception e) {
			// TODO: handle exception
		}
		LOGGER.info("dopost method #####################");
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
