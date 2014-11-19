/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtz.spring.dao;

import com.mtz.spring.dto.User;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author salemmo
 */
public class UserDAOImpl implements UserDAO{

    @Override
    public User getUser(int id) {
        Session session = sessionFactory.getCurrentSession();
        User u = (User) session.get(User.class, id);
        return u;
    }

    @Override
    public User addUser(User user) {
    	 test("http://localhost:8082/EmployerInfoService/EmployerServlet");
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(user);
        return user;
    }

    @Override
    public User removeUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(user);
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from User").list();
    }
    
    private SessionFactory sessionFactory;
    /**
     * @return the sessionFactory
     */
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * @param sessionFactory the sessionFactory to set
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public static void test(String address) {
    	
            try{
    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                    URL serverAddress = new URL(address);
                    HttpURLConnection connection = null;
                    connection = (HttpURLConnection) serverAddress.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setDoOutput(true);
                    connection.setReadTimeout(10000);
                    connection.connect();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    connection.disconnect();
                    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    
    
    
    
}
