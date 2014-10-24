package com.appdynamics.util;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;

public class HibernateUtil{
private static final SessionFactory sessionFactory;
static {
 try {

	 sessionFactory = new Configuration().configure(
             "hibernate.cfg.xml").buildSessionFactory();
 
 } catch (Throwable ex) {
  System.err.println("Initial SessionFactory creation failed." + ex);
  throw new ExceptionInInitializerError(ex);
 }
}

public static SessionFactory getSessionFactory() {
 return sessionFactory;
}
public static Session getSession() {
    Session s = null;
    try {
        s = sessionFactory.openSession();
    } catch (HibernateException e) {
        System.out.println(e.getMessage());
    }
    return s;
}

}
