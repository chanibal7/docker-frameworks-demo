package com.samplewebservice.webservice.ws;
 
import javax.jws.WebService;
 
//Service Implementation
@WebService(endpointInterface = "com.samplewebservice.webservice.ws.AppraisalSystem")
public class AppraisalSystemImpl implements AppraisalSystem{

	public String getAppraisal(String name) {
		return "Welcome to Employee Management World " + name;
	}
 
	
 
}