package com.appdynamics.src;
import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.springframework.boot.SpringApplication;

// Extend HttpServlet class
public class RabbitMQ extends HttpServlet {
 
  private String message;

  public void init() throws ServletException
  {
      // Do required initialization
      message = "RabbitMQ success >>>>>>>>";
  }

  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
            throws ServletException, IOException
  {
      // Set response content type
      response.setContentType("text/html");
      SpringApplication.run(Application.class);
      // Actual logic goes here.
      PrintWriter out = response.getWriter();
      out.println("<h1>" + message + "</h1>");
  }
  
  public void destroy()
  {
      // do nothing.
  }
}