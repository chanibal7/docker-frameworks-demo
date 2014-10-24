package com.sample.servlet;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;






public class TransportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static Logger LOGGER = Logger.getLogger(TransportServlet.class .getName()); 
	
       
    
    public TransportServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOGGER.setLevel(Level.INFO); 
		LOGGER.info("Class:TransportServlet###################");
		LOGGER.info("doget method #####################");
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO 
		LOGGER.setLevel(Level.INFO); 
		LOGGER.info("Class:TransportServlet###################");
		LOGGER.info("dopost method #####################");
		
	}

}
