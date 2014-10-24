package com.appdynamics;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.appdynamics.customer.model.Customer;

@ManagedBean(name="customer")
@SessionScoped
public class CustomerBean implements Serializable{
	 static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	  static final String DB_URL = "jdbc:mysql://localhost/jsfdb";

	   //  Database credentials
	   static final String USER = "root";
	   static final String PASS = "root";
	//if resource inject is not support, you still can get it manually.
	/*public CustomerBean(){
		try {
			Context ctx = new InitialContext();
			ds = (DataSource)ctx.lookup("java:comp/env/jdbc/mkyongdb");
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
	}*/
	
	//connect to DB and get customer list
	public List<Customer> getCustomerList() throws Exception{
		
		System.out.println("-----server-------");
		Class.forName("com.mysql.jdbc.Driver");
		//get database connection
		Connection con = DriverManager.getConnection(DB_URL,USER,PASS);
		
		if(con==null)
			throw new SQLException("Can't get database connection");
		
		PreparedStatement ps 
			= con.prepareStatement(
				"select customer_id, name, address, created_date from customer"); 
		
		//get customer data from database
		ResultSet result =  ps.executeQuery();
		
		List<Customer> list = new ArrayList<Customer>();
		
		while(result.next()){
			Customer cust = new Customer();
			
			cust.setCustomerID(result.getLong("customer_id"));
			cust.setName(result.getString("name"));
			cust.setAddress(result.getString("address"));
			cust.setCreated_date(result.getDate("created_date"));
			
			//store all data into a List
			list.add(cust);
		}
			
		return list;
	}
}
