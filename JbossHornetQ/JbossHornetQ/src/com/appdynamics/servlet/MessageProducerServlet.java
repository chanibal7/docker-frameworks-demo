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
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * User: jayesh
 * Date: Feb 18, 2009
 */
public class MessageProducerServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) {
        try {

            InitialContext ctx = new InitialContext();
            ConnectionFactory factory = (ConnectionFactory) ctx.lookup((String) request.getParameter
                    ("connectionfactory"));
            Connection connection = factory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination dest = (Destination) ctx.lookup((String) request.getParameter("destination"));

            MessageProducer prod = session.createProducer(dest);
//        printAllMethods(prod.getClass());
            TextMessage msg = session.createTextMessage();
            msg.setText("Testing123");
            msg.setStringProperty("stringProperty", "propertytest");
            prod.send(msg);
            //  prod.close();
            PrintWriter out = response.getWriter();
            out.println("\nText Message Sent successfully " + msg.getText());

            MapMessage message = session.createMapMessage();
            message.setString("MapTest", "singularity");
            prod.send(message);
            prod.close();
            session.close();
            out.println("\nMap Message Sent successfully " + msg.getText());
            out.println("---->" + prod.getClass().getName());
            connection.close();
            response.getWriter().println("\nMessage Sent successfully  at " + new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void searchForClass(String dir, String pattern) throws IOException {
        File[] jarFiles = new File(dir).listFiles();
        for (File jarFile : jarFiles) {
            if (jarFile.getName().endsWith(".jar")) {
                ZipEntry entry;
                String entryName;
                ZipInputStream zis = new ZipInputStream(new FileInputStream(jarFile));
                while ((entry = zis.getNextEntry()) != null) {
                    if (entry.getName().contains(pattern)) {
                        System.out.println("Found Pattern -" + pattern);
                        System.out.println("File -" + jarFile.getAbsolutePath());
                    }
                }

            }
        }
    }


    public static String printAllMethods(Class cls) {
        System.out.println("Class Name (((((((((((((((((((((((((())))))))))))))))))))))))))" + cls.getName());
        if (cls.getProtectionDomain() != null && cls.getProtectionDomain().getCodeSource() != null) {
            System.out.println("Location ((((((((((((((((( " + cls.getProtectionDomain().getCodeSource().getLocation());
        }
        System.out.println("Interfaces  (((((((((((((((((((((((((())))))))))))))))))))))))))");
        Class[] intfs = cls.getInterfaces();
        for (Class intf : intfs) {
            System.out.println("Interface ################# " + intf.getName());
        }
        System.out.println("End Interfaces  (((((((((((((((((((((((((())))))))))))))))))))))))))");
        System.out.println("Public  Methods  (((((((((((((((((((((((((())))))))))))))))))))))))))");

        Method[] methods = cls.getMethods();
        for (Method method : methods) {
            System.out.println("Method >" + method.getName() + "Desc >" + method);
        }
        System.out.println("End Public  Methods  (((((((((((((((((((((((((())))))))))))))))))))))))))");

        System.out.println("All  Methods  (((((((((((((((((((((((((())))))))))))))))))))))))))");

        Method[] methods1 = cls.getDeclaredMethods();
        for (Method method1 : methods1) {
            System.out.println("Declared Method >" + method1.getName() + "Desc >" + method1);
        }
        System.out.println("All Methods  (((((((((((((((((((((((((())))))))))))))))))))))))))");


        System.out.println("End Class Name (((((((((((((((((((((((((())))))))))))))))))))))))))" + cls.getName());
        return "";
    }


}
