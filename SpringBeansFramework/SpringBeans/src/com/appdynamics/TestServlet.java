package com.appdynamics;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TestServlet extends HttpServlet {
           
            private static final long serialVersionUID = 1L;

            public void doGet(HttpServletRequest request, HttpServletResponse response)
                                    throws ServletException, IOException {
                       
                        ApplicationContext context = 
                                new ClassPathXmlApplicationContext("../applicationContext.xml");

                         StudentJDBCTemplate studentJDBCTemplate = 
                         (StudentJDBCTemplate)context.getBean("studentJDBCTemplate");
                         
                         System.out.println("------Records Creation--------" );
                         studentJDBCTemplate.create("Zara", 11);
                         studentJDBCTemplate.create("Nuha", 2);
                         studentJDBCTemplate.create("Ayan", 15);

                         System.out.println("------Listing Multiple Records--------" );
                         List<Student> students = studentJDBCTemplate.listStudents();
                         for (Student record : students) {
                            System.out.print("ID : " + record.getId() );
                            System.out.print(", Name : " + record.getName() );
                            System.out.println(", Age : " + record.getAge());
                         }

                         System.out.println("----Updating Record with ID = 2 -----" );
                         studentJDBCTemplate.update(2, 20);

                         System.out.println("----Listing Record with ID = 2 -----" );
                         Student student = studentJDBCTemplate.getStudent(2);
                         System.out.print("ID : " + student.getId() );
                         System.out.print(", Name : " + student.getName() );
                         System.out.println(", Age : " + student.getAge());
                       
            }

}