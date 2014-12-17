package com.wicket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;

import com.wicket.hello.Hello;

public class WicketApplication extends WebApplication {

	@Override
	public Class<? extends Page> getHomePage() {
		System.out.println("HELLO........................ONE");
		String st1 = test("http://localhost:8081/EmployerInfoService/EmployerServlet");
		System.out.println("HELLO........................TWO");
		return Hello.class; // return default page
	}

	public String test(String address) {
		System.out
				.println("************** inside test method *********************");
		try {
			URL serverAddress = new URL(address);
			HttpURLConnection connection = null;
			connection = (HttpURLConnection) serverAddress.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setReadTimeout(10000);
			connection.connect();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			connection.disconnect();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

}
