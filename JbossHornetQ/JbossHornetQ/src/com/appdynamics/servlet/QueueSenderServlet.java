package com.appdynamics.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;

import java.io.IOException;
import java.util.Date;
import java.util.Hashtable;

/**
 * User: jayesh
 * Date: May 13, 2009
 */
public class QueueSenderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) {
        try {
            InitialContext ctx = new InitialContext();
            QueueConnectionFactory qConnectionFactory = (QueueConnectionFactory) ctx.lookup((String)request.getParameter("connectionfactory"));
            QueueConnection qConnection = qConnectionFactory.createQueueConnection();
            QueueSession qSession = qConnection.createQueueSession(false, javax.jms.QueueSession.AUTO_ACKNOWLEDGE);
            QueueSender qSender = qSession.createSender(((Queue) ctx.lookup((String)request
                    .getParameter("destination"))));
            System.out.println("Sending messages ");
            TextMessage textMsg = qSession.createTextMessage();
            for (int i = 1; i <= 10; i++) {
                textMsg.clearBody();
                textMsg.setIntProperty("severity", i);
                textMsg.setText("Message #" + i);
                System.out.println(" Sending message: Message #" + i);
                qSender.send(textMsg);
            }
            textMsg.clearBody();
            textMsg.setIntProperty("severity", 0);
            textMsg.setText("Stop");
            System.out.println(" Sending message: Stop");
            qSender.send(textMsg);
            System.out.println("Messages Sent Successfully");
             response.getWriter().println("Message Sent successfully to Queue at "+new Date());
            qSender.close();
            qSession.close();
            qConnection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
