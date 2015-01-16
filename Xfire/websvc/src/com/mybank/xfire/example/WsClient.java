package com.mybank.xfire.example;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.service.binding.ObjectServiceFactory;
import org.codehaus.xfire.XFire;
import org.codehaus.xfire.XFireFactory;
import org.apache.log4j.Logger;

/**
 * Servlet working as a Web services client.
 * 
 */
public class WsClient extends HttpServlet {
	private static Logger log = Logger.getLogger(WsClient.class);

	/*
	 * doGet():
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// get request parameters
		String fromAccount = req.getParameter("from");
		String toAccount = req.getParameter("to");
		String strAmount = req.getParameter("amt");
		String currency = req.getParameter("cur");
		double amount = 500.00;
		log.info("Class:WsClient###################");
		// use default values wherever needed
		if (null != strAmount) {
			try {
				amount = Double.parseDouble(strAmount);
			} catch (NumberFormatException ne) {
				// use the default amount
			}
		}
		if (null == fromAccount) {
			fromAccount = "11111-01234";
		}
		if (null == toAccount) {
			toAccount = "99999-05678";
		}
		if (null == currency) {
			currency = "CDN$";
		}

		// invoke callWebService()

		String responseGot = "";
		try {
			responseGot = callWebService(fromAccount, toAccount, amount,
					currency);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// prepare the response page
		resp.setContentType("text/html");
		java.io.PrintWriter out = resp.getWriter();
		out.println("<html>");
		out.println("<head><title>My Bank - Funds Transfer Service </title></head><body>");
		out.println("<h2>My Bank - Funds Transfer Service</h2>");
		NumberFormat formatter = new DecimalFormat("###,###,###,###.00");
		out.println("<B>Service Request</B>" + "<br>" + "Transfer funds - "
				+ "<TABLE>" + "<TR><TD>currency: </TD><TD>" + currency
				+ "</TD></TR>" + "<TR><TD>amount: </TD><TD>"
				+ formatter.format(amount) + "</TD></TR>"
				+ "<TR><TD>from account: </TD><TD>" + fromAccount + "<br>"
				+ "</TD></TR>" + "<TR><TD>to account: </TD><TD>" + toAccount
				+ "</TD></TR>" + "</TABLE>");

		out.println("<br>" + "<B>Response Received</B>" + "<br>" + responseGot);
		out.println("</body></html>");
	}

	/*
	 * call the web service
	 */
	public String callWebService(String fromAccount, String toAccount,
			double amount, String currency) throws MalformedURLException,
			Exception {
		// create a metadata of the service
		Service serviceModel = new ObjectServiceFactory()
				.create(IBankingService.class);
		Thread.sleep(2000);
		// create a proxy for the deployed service
		XFire xfire = XFireFactory.newInstance().getXFire();
		XFireProxyFactory factory = new XFireProxyFactory(xfire);
		// String serviceUrl = "xfire.local://Banking" ;
		String serviceUrl = "http://localhost:8080/websvc/services/Banking";
		IBankingService client = null;
		try {
			client = (IBankingService) factory.create(serviceModel, serviceUrl);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		// invoke the service
		String serviceResponse = "";
		try {
			serviceResponse = client.transferFunds(fromAccount, toAccount,
					amount, currency);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// return the response
		return serviceResponse;
	}

	/*
	 * doPost():
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

	static String SPACE = "&nbsp; &nbsp;";

}