package com.appdynamics.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.naming.*;
import javax.jms.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * User: jayesh
 * Date: Feb 18, 2009
 */
public class TopicServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
               process(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
               process(request,response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) {
       try{
         
        InitialContext ctx = new InitialContext();
        ConnectionFactory factory =(ConnectionFactory) ctx.lookup("connectionfactory");
        Connection connection = factory.createConnection();
        Session session =connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        Destination dest=(Destination)ctx.lookup("destination");
        MessageProducer prod=session.createProducer(dest);
//        printAllMethods(prod.getClass());
        TextMessage msg = session.createTextMessage();
        msg.setText("Testing123");
        msg.setStringProperty("stringProperty","propertytest");
        prod.send(msg);
        prod.close();
        PrintWriter out=response.getWriter();
        out.println("---->"+prod.getClass().getName());
        out.println("Message Sent successfully "+ msg.getText());
        connection.close();
        System.out.println("Message Sent Successfully");
       }catch (Exception e){
           e.printStackTrace();
       }

    }

       public static void searchForClass(String dir,String pattern) throws IOException
    {
        File[] jarFiles = new File(dir).listFiles();
        for (File jarFile:jarFiles)
        {
            if (jarFile.getName().endsWith(".jar"))
            {
                ZipEntry entry;
                String entryName;
                ZipInputStream zis = new ZipInputStream(new FileInputStream(jarFile));
                while ((entry = zis.getNextEntry()) != null)
                {
                    if(entry.getName().contains(pattern))
                    {
                        System.out.println("Found Pattern -"+pattern);
                        System.out.println("File -"+jarFile.getAbsolutePath());
                    }
                }

            }
        }
    }




}
