package com.postgressql.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("postgressqlService")
public class PostgresSQLService {

	private final static Logger LOGGER = Logger
			.getLogger(PostgresSQLService.class.getName());

	static final String DRIVER_NAME = "org.postgresql.Driver";
	static final String DB_URL = "jdbc:postgresql://localhost:5432/";
	static final String DB_NAME = "testdb";

	static final String USER = "postgres";
	static final String PASS = "root";

	Connection conn = null;
	Statement stmt = null;

	@GET
	@Path("/postgressqlOperation")
	@Produces(MediaType.APPLICATION_XML)
	public String postgressqlTest() throws InterruptedException {
		LOGGER.setLevel(Level.INFO);

		System.out
				.println("#####################  Performing CURD operation on PostgresSQL! ##############################");

		createpostgresDB();

		createpostgresTable();

		insertpostgresRecord();

	 	updatepostgresRecord();

		deletepostgresRecord();

	 	droppostgresTable();

		droppostgresDB();

		System.out
				.println("####################  Done with CURD operation on PostgresSQL! ###################################");
		return "success";
	}

	private void createpostgresDB() {
		try {
			String SQL = "CREATE DATABASE" + " " + DB_NAME;
			postgresCurdOperation(DB_URL, USER, PASS, SQL, false);

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Database created successfully");
	}


	private void createpostgresTable() {
		try {
			String SQL = "CREATE TABLE COMPANY "
					+ "(ID INT PRIMARY KEY     NOT NULL,"
					+ " NAME           TEXT    NOT NULL, "
					+ " AGE            INT     NOT NULL, "
					+ " ADDRESS        CHAR(50), " + " SALARY         REAL)";
			postgresCurdOperation(DB_URL + DB_NAME, USER, PASS, SQL, false);
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Table created successfully");
	}

	private void insertpostgresRecord() {
		try {

			String SQL = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) "
					+ "VALUES (1, 'Paul', 32, 'California', 20000.00 );";
			postgresCurdOperation(DB_URL + DB_NAME, USER, PASS, SQL, true);

			SQL = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) "
					+ "VALUES (2, 'Allen', 25, 'Texas', 15000.00 );";
			postgresCurdOperation(DB_URL + DB_NAME, USER, PASS, SQL, true);

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Above Records inserted successfully");
	}

	private void updatepostgresRecord() {
		try {
			String SQL = "UPDATE COMPANY set SALARY = 25000.00 where ID=1;";
			postgresCurdOperation(DB_URL + DB_NAME, USER, PASS, SQL, true);

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Update record done successfully");
	}

	private void deletepostgresRecord() {
		try {
			String SQL = "DELETE from COMPANY where ID=2;";
			postgresCurdOperation(DB_URL + DB_NAME, USER, PASS, SQL, true);
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Delete record done successfully");
	}

	private void droppostgresTable() {
		try {
			String SQL = "DROP TABLE COMPANY";
			postgresCurdOperation(DB_URL + DB_NAME, USER, PASS, SQL, false);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Table droped successfully");
	}

	private void droppostgresDB() {
		try {
			String SQL = "DROP DATABASE" + " " + DB_NAME;
			postgresCurdOperation(DB_URL, USER, PASS, SQL, false);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Database droped successfully");
	}

	private void postgresCurdOperation(String db_url, String user, String pwd,
			String sql, boolean flag) {

		String DB_URL = db_url;
		String USER = user;
		String PWD = pwd;
		String SQL = sql;

		try {
			Class.forName(DRIVER_NAME);
			conn = DriverManager.getConnection(DB_URL, USER, PWD);
			if (flag)
				conn.setAutoCommit(false);

			stmt = (Statement) conn.createStatement();
			stmt.executeUpdate(SQL);
			
			if (flag)
				conn.commit();
			
			if (flag) {
				ResultSet rs = stmt.executeQuery("SELECT * FROM COMPANY;");
				while (rs.next()) {
					int id = rs.getInt("id");
					String name = rs.getString("name");
					int age = rs.getInt("age");
					String address = rs.getString("address");
					float salary = rs.getFloat("salary");
					System.out.println("ID = " + id);
					System.out.println("NAME = " + name);
					System.out.println("AGE = " + age);
					System.out.println("ADDRESS = " + address);
					System.out.println("SALARY = " + salary);
					System.out.println();
				}
				rs.close();
			}

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
