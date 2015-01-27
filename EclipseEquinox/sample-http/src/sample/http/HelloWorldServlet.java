package sample.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HelloWorldServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		System.out.println(">>>>>>>>>>>>>>>>>>>>  HelloWORLDsERVLET <<<<<<<<<<<<<<<<<<<<<<<<<<<<<,");
			resp.setContentType("text/html");
		resp.getWriter().write("<html><body>Docker Hello World -- sample servlet</body></html>"); //$NON-NLS-1$
	}

}
