package com.appdynamics.webservices;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.appdynamics.HelloWorldLocal;

@Path("ejbService")
public class EjbService {
	@GET
	@Path("/ejbOperation")
	@Produces(MediaType.APPLICATION_XML)
	public String getUser() {
		System.out.println("-----------<<<<Server start>>>------------");
		try {
			 String ejbcontent=ejbMethod();
			 System.out.println(ejbcontent+">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			return "1";

		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}
	@EJB private HelloWorldLocal hello;
	public String ejbMethod(){
		return  hello.sayHello();
	}
}
