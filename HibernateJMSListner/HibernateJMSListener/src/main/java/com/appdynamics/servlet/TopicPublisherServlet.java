package com.appdynamics.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.jms.*;
import javax.naming.InitialContext;

import org.hibernate.Transaction;

import com.appdynamics.model.Student;
import com.appdynamics.util.HibernateUtil;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Date;
import java.util.List;

/**
 * User: jayesh
 * Date: May 13, 2009
 */
public class TopicPublisherServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	try {
    		org.hibernate.Session hsession = HibernateUtil.getSession();
			Transaction tx = hsession.beginTransaction();
			List<Student> students = hsession.createSQLQuery(
					"SELECT * FROM student").list();
			if (students.size() > 0) {
				System.out.println("students.size()="+students.size()); 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) {
        try {
            InitialContext ctx = new InitialContext();
            TopicConnectionFactory topicConnectionFactory = (TopicConnectionFactory) ctx.lookup((String)request.getParameter("connectionfactory"));
            TopicConnection tConnection = topicConnectionFactory.createTopicConnection();
            TopicSession topicSession = tConnection.createTopicSession(false, TopicSession.AUTO_ACKNOWLEDGE);
            TopicPublisher topicPublisher = topicSession.createPublisher(((Topic) ctx.lookup((String)request.getParameter("destination"))));
            System.out.println("Sending messages ");
            TextMessage textMsg = topicSession.createTextMessage();
            for (int i = 1; i <= 10; i++) {
                textMsg.clearBody();
                textMsg.setIntProperty("severity", i);
                textMsg.setText("Message #" + i);
                System.out.println(" Sending message: Message #" + i);
                topicPublisher.send(textMsg);
            }
            textMsg.clearBody();
            textMsg.setIntProperty("severity", 0);
            textMsg.setText("Stop");
            System.out.println(" Sending message: Stop");
            topicPublisher.send(textMsg);
            System.out.println("Messages Sent Successfully");
            response.getWriter().println("Message Sent successfully to Topic at "+new Date());
            topicPublisher.close();
            topicSession.close();
            tConnection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
}