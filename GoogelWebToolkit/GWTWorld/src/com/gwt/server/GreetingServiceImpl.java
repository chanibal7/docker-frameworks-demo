package com.gwt.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.gwt.client.GreetingService;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
		GreetingService {
 
	
	private final static Logger LOGGER = Logger.getLogger(GreetingServiceImpl.class
			.getName());

	
	public void test(String address) {
		LOGGER.info("Class:GreetingServletImpl###################");
        System.out.println("************** inside GreetingServletImp test method *********************");
        try{
                URL serverAddress = new URL(address);
                HttpURLConnection connection = null;
                connection = (HttpURLConnection) serverAddress.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setReadTimeout(10000);
                connection.connect();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                connection.disconnect();
                
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


	@Override
	public String greetServer(String name) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}
}
