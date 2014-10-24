package com.appdynamics;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class EJBTest
 */
public class EJBTest extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
	@EJB private HelloWorldLocal hello;	
    public EJBTest() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Do get>>>>>>>>>>>>>>>>");
		String hellodetails=hello.sayHello(); 
		System.out.println(" "+hellodetails+" >>>>>>>>>>>");
		// TODO Auto-generated method stub
	}

}
