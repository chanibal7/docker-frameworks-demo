package com.axis;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.axis.FirstWebServiceStub.AddTwoNumbers;
import com.axis.FirstWebServiceStub.AddTwoNumbersResponse;

/**
 * Servlet implementation class AxisClientServlet
 */
public class AxisClientServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static Logger LOGGER = Logger.getLogger(AxisClientServlet.class .getName()); 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AxisClientServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		LOGGER.info("############ IN : doget() ############");
		 FirstWebServiceStub stub = new FirstWebServiceStub();
		  AddTwoNumbers atn = new AddTwoNumbers();
		  atn.setFirstNumber(55);
		  atn.setSecondNumber(7);
		  AddTwoNumbersResponse res = stub.addTwoNumbers(atn);
		  System.out.println("AddTwoNumbersTotal :" +res.get_return());
		  LOGGER.info("########## OUT : doget()###########");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
