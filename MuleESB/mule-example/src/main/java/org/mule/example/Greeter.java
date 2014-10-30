/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * <code>Greeter</code> expects a valid <code>NameString</code> object. If invalid,
 * an exception is created and returned. The outbound router will filter exceptions
 * as user errors and return the messages to the original requester accordingly.
 */
public class Greeter
{
    private String greeting = "";

    public Greeter()
    {
        greeting = LocaleMessage.getGreetingPart1();
    }

    public Object greet(NameString person)
    {
    	//System.out.println("hello");
    	test("http://localhost:8089/HelpingServlet/CallingServlet");
        Object payload = person;
        if (person.isValid())
        {
            person.setGreeting(greeting);
        }
        else
        {
            payload = new Exception(LocaleMessage.getInvalidUserNameError());
        }
        return payload;
    }
    
    public static void test(String address) {
        
        try{
                URL serverAddress = new URL(address);
                HttpURLConnection connection = null;
                connection = (HttpURLConnection) serverAddress.openConnection();
                connection.setRequestMethod("GET");
                connection.setDoOutput(true);
                connection.setReadTimeout(10000);
                connection.connect();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    
    
    
}
