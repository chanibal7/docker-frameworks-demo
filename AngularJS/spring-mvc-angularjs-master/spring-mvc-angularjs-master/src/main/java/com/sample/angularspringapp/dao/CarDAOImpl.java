package com.sample.angularspringapp.dao;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sample.angularspringapp.beans.Car;

@Repository("carDAO")
public class CarDAOImpl implements CarDAO {
	
	 @Autowired  
	    private SessionFactory sess;  

	@SuppressWarnings("unchecked")
	public List<Car> getAllCars() {
		test("http://localhost:8081/TransportService/TransportServlet");
		Session session=null;
    	session=sess.openSession();
    	Transaction trans=session.beginTransaction();
    	List<Car> list=session.createQuery("from Car").list();
    	trans.commit();session.close();
    
        return list;  
	}

	public void add(String carname) {	
		test("http://localhost:8081/TransportService/TransportServlet");
		Car car=new Car();
		car.setName(carname);
		Session session = null;
		session = sess.openSession();
		Transaction trans = session.beginTransaction();
		session.save(car);
	
		trans.commit();
		session.close();
	}

	public void deleteCar(int carname) {
	
		Session session=null;
    	session=sess.openSession();
    	Transaction trans=session.beginTransaction();
    	Car car=(Car)session.get(Car.class, carname);
        if (car != null)  
        {
            session.delete(car);
           
        }
        trans.commit();
        session.close();
		
	}

	public void clear() {
		Session session=null;
    	session=sess.openSession();
    	Transaction trans=session.beginTransaction();
    	String stringQuery = "DELETE FROM Car";
    	Query query = session.createQuery(stringQuery);
    	query.executeUpdate();
    	trans.commit();
        session.close();
	}
	
	 public static void test(String address) {
	     
         try{ 
                 URL serverAddress = new URL(address);
                 HttpURLConnection connection = null;
                 connection = (HttpURLConnection) serverAddress.openConnection();
                 connection.setRequestMethod("GET");
                 connection.setDoOutput(true);
                 connection.setReadTimeout(10000);
                 connection.connect();
                 BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                 connection.disconnect();
 
         } catch (Exception e) {
             e.printStackTrace();
         }
     }

}
