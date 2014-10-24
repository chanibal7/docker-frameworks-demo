package com.mysql.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.mysql.jdbc.Statement;

@Path("mysqlService")
public class MySQLService {

	private final static Logger LOGGER = Logger.getLogger(MySQLService.class
			.getName());

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/";
	static final String DB_NAME = "STUDENTS";

	static final String USER = "root";
	static final String PASS = "root";

	Connection conn = null;
	Statement stmt = null;

	@GET
	@Path("/mysqlOperation")
	@Produces(MediaType.APPLICATION_XML)
	public String mysqlTest() throws InterruptedException {

		LOGGER.setLevel(Level.INFO);
		System.out.println("Performing CURD operation on MySQL!");
		createDataBase();
		Thread.sleep(2000);
		createTable();
		Thread.sleep(2000);
		insertRecord();
		Thread.sleep(2000);
		queryRecord();
		Thread.sleep(2000);
		updateRecord();
		/*Thread.sleep(2000);
		queryRecord();*/
		Thread.sleep(2000);
		deleteRecord();
		Thread.sleep(2000);
		deleteDataBase();
		Thread.sleep(2000);
		System.out.println("Done with CURD opertation on MySQL!");
		return "success";

	}

	private void createDataBase() {
			System.out.println("Connecting to database...");
			String SQL = "CREATE DATABASE"+" "+DB_NAME;
			System.out.println("Creating database...");
			curdOperation(DB_URL, USER, PASS,SQL);
			System.out.println("Database created successfully...");
	
	}

	private void createTable() {
			System.out.println("Creating table in given database...");
			String SQL = "CREATE TABLE REGISTRATION "
					+ "(id INTEGER not NULL, " + " first VARCHAR(255), "
					+ " last VARCHAR(255), " + " age INTEGER, "
					+ " PRIMARY KEY ( id ))";
			curdOperation(DB_URL + DB_NAME, USER, PASS,SQL);
			System.out.println("Created table in given database..."); 

	}

	private void insertRecord() {
			System.out.println("Inserting records into the table...");
			String SQL = "INSERT INTO REGISTRATION " + "VALUES (100, 'Zara', 'Ali', 18)";
			curdOperation(DB_URL + DB_NAME, USER, PASS,SQL);
			SQL = "INSERT INTO REGISTRATION " + "VALUES (101, 'Mahnaz', 'Fatma', 25)";
			curdOperation(DB_URL + DB_NAME, USER, PASS,SQL);
			SQL = "INSERT INTO REGISTRATION " + "VALUES (102, 'Zaid', 'Khan', 30)";
			curdOperation(DB_URL + DB_NAME, USER, PASS,SQL);
			SQL = "INSERT INTO REGISTRATION " + "VALUES(103, 'Sumit', 'Mittal', 28)";
			curdOperation(DB_URL + DB_NAME, USER, PASS,SQL);
			System.out.println("Inserted records into the table...");
	}

	private void updateRecord() {
		  System.out.println("Updating records...");
			String SQL = "UPDATE REGISTRATION "
					+ "SET age = 30 WHERE id in (100, 101)";
			curdOperation(DB_URL + DB_NAME, USER, PASS,SQL);
			System.out.println("Updated records...");
	}

	private void queryRecord() {
		try {

			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL + DB_NAME, USER, PASS);
			stmt = (Statement) conn.createStatement();
			String sql = "SELECT id, first, last, age FROM REGISTRATION";
			ResultSet rs = stmt.executeQuery(sql);
			// STEP 5: Extract data from result set
			while (rs.next()) {
				// Retrieve by column name
				int id = rs.getInt("id");
				int age = rs.getInt("age");
				String first = rs.getString("first");
				String last = rs.getString("last");

				// Display values
				System.out.print("ID: " + id);
				System.out.print(", Age: " + age);
				System.out.print(", First: " + first);
				System.out.println(", Last: " + last);
			}
			rs.close();

		} catch (SQLException se) {

			se.printStackTrace();
		} catch (Exception e) {

			e.printStackTrace();
		} finally {

			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
	}

	private void deleteRecord() {
			System.out.println("Deleting records from the table...");
			String SQL = "DELETE FROM REGISTRATION " + "WHERE id = 101";
			curdOperation(DB_URL + DB_NAME, USER, PASS,SQL);
			System.out.println("Deleted records from the table...");
	}

	private void deleteDataBase() {
			System.out.println("Deleting database ...");
			String SQL = "DROP DATABASE STUDENTS";
			curdOperation(DB_URL + DB_NAME, USER, PASS,SQL);
			System.out.println("Database deleted successfully...");
	}
	
	private void curdOperation(String db_url, String user, String pwd,
			String sql) {

		String DB_URL = db_url;
		String USER = user;
		String PWD = pwd;
		String SQL = sql;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PWD);
			stmt = (Statement) conn.createStatement();
			stmt.executeUpdate(SQL);
			
		} catch (SQLException se) {

			se.printStackTrace();
		} catch (Exception e) {

			e.printStackTrace();
		} finally {

			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}

	}
}
