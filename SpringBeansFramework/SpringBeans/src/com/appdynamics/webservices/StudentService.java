package com.appdynamics.webservices;

import java.util.Date;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.appdynamics.Student;
import com.appdynamics.StudentJDBCTemplate;

@Path("springService")
public class StudentService {
	@GET
	@Path("/studentOperation")
	@Produces(MediaType.APPLICATION_XML)
	public String getStudentList() {
		System.out.println("-----------<<<<Server start>>>------------");
		try {

			ApplicationContext context = new ClassPathXmlApplicationContext(
					"../applicationContext.xml");
			Date date = (Date) context.getBean("dateBean");
			StudentJDBCTemplate studentJDBCTemplate = (StudentJDBCTemplate) context
					.getBean("studentJDBCTemplate");

			System.out.println("------Records Creation--------");
			studentJDBCTemplate.create("Zara", 11);
			studentJDBCTemplate.create("Nuha", 2);
			studentJDBCTemplate.create("Ayan", 15);

			System.out.println("------Listing Multiple Records--------");
			List<Student> students = studentJDBCTemplate.listStudents();
			for (Student record : students) {
				System.out.print("ID : " + record.getId());
				System.out.print(", Name : " + record.getName());
				System.out.println(", Age : " + record.getAge());
			}
			return students.size() + "";

		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

}
