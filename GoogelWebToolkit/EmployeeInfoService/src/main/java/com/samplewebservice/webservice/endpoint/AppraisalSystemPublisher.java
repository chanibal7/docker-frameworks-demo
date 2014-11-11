package com.samplewebservice.webservice.endpoint;

import javax.xml.ws.Endpoint;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.samplewebservice.webservice.ws.AppraisalSystemImpl;

//Endpoint publisher
@Controller
public class AppraisalSystemPublisher {

	@RequestMapping(value = "/gettingappraisal")
	public String publishWebService() {
		Endpoint.publish("http://localhost:9991/ws/appraisal",
				new AppraisalSystemImpl());
		return "hello";

	}

}