package com.glassfish.service;

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

@Path("glassfishService")
public class GlassFishService {

	private final static Logger LOGGER = Logger.getLogger(GlassFishService.class
			.getName());

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/";
	static final String DB_NAME = "GLASSFISHCART";

	static final String USER = "root";
	static final String PASS = "root";

	Connection conn = null;
	Statement stmt = null;

	@GET
	@Path("/glassfishOperation")
	@Produces(MediaType.APPLICATION_XML)
	public String mysqlTest() throws InterruptedException {

		LOGGER.setLevel(Level.INFO);
		System.out.println("Performing CURD operation on GLASSFISHCART!");
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
		System.out.println("Done with CURD opertation on GLASSFISHCART!");
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
			String SQL = "CREATE TABLE CATALOGUE "
					+ "(id INTEGER not NULL, " + " mobile VARCHAR(255), "
					+ " price INTEGER, " + " series VARCHAR(255), "
					+ " os VARCHAR(255), " + " PRIMARY KEY ( id ))";
			curdOperation(DB_URL + DB_NAME, USER, PASS,SQL);
			System.out.println("Created table in given database..."); 

	}

	private void insertRecord() {
			System.out.println("Inserting records into the table...");
			String SQL = "INSERT INTO CATALOGUE " + "VALUES (100, 'i-phone', 3000, 's1','mac')";
			curdOperation(DB_URL + DB_NAME, USER, PASS,SQL);
			SQL = "INSERT INTO CATALOGUE " + "VALUES (101, 'gogl-phone', 1500, 'gl12','gogl')";
			curdOperation(DB_URL + DB_NAME, USER, PASS,SQL);
			System.out.println("Inserted records into the table...");
	}

	private void updateRecord() {
		  System.out.println("Updating records...");
			String SQL = "UPDATE CATALOGUE "
					+ "SET price = 4500 WHERE id in (101)";
			curdOperation(DB_URL + DB_NAME, USER, PASS,SQL);
			System.out.println("Updated records...");
	}

	private void queryRecord() {
		try {

			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL + DB_NAME, USER, PASS);
			stmt = (Statement) conn.createStatement();
			String sql = "SELECT id, mobile, price, series, os FROM CATALOGUE";
			ResultSet rs = stmt.executeQuery(sql);
			// STEP 5: Extract data from result set
			while (rs.next()) {
				// Retrieve by column name
				int id = rs.getInt("id");
				String mobile = rs.getString("mobile");
				int price = rs.getInt("price");
				String series = rs.getString("series");
				String os = rs.getString("os");

				// Display values
				System.out.print("ID: " + id);
				System.out.print(", MOBILE: " + mobile);
				System.out.println("PRICE: " + price);
				System.out.print(", SERIES: " + series);
				System.out.println(", OS: " + os);
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
			String SQL = "DELETE FROM CATALOGUE " + "WHERE id = 101";
			curdOperation(DB_URL + DB_NAME, USER, PASS,SQL);
			System.out.println("Deleted records from the table...");
	}

	private void deleteDataBase() {
			System.out.println("Deleting database ...");
			String SQL = "DROP DATABASE GLASSFISHCART";
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
