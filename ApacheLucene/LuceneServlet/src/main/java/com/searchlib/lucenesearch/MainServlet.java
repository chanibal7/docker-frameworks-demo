package com.searchlib.lucenesearch;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static Logger LOGGER = Logger.getLogger(MainServlet.class .getName()); 
	
       
    
    public MainServlet() {
        super();
        
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOGGER.setLevel(Level.INFO); 
		
		LOGGER.info("Class:MainServlet###################");
		
		SimpleFileIndexer simpleFileIndexer= new SimpleFileIndexer();
		SimpleSearcher simpleSearcher=new SimpleSearcher();
		try {
			simpleFileIndexer.doIndexing();
			simpleSearcher.doSearching();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LOGGER.info("doget method #####################");
	
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOGGER.setLevel(Level.INFO); 
		LOGGER.info("Class:MainServlet###################");
		LOGGER.info("dopost method #####################");
	}
	
	
	

}
