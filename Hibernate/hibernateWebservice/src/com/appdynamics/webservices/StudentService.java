package com.appdynamics.webservices;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.appdynamics.model.Student;
import com.appdynamics.util.HibernateUtil;

@Path("hibernateService")
public class StudentService {
	@GET
	@Path("/getStudentList")
	@Produces(MediaType.APPLICATION_XML)
	public String getStudentList() {
		System.out.println("-----------<<<<Server start>>>------------");
		try {
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			List<Student> students = session.createSQLQuery(
					"SELECT * FROM student").list();
			if (students.size() > 0) {
				return students.size() + "";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "0";
	}

}
