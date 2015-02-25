package com.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class MyWorldGWT implements EntryPoint {

	/**
	 * Create a remote service proxy to talk to the server-side Greeting
	 * service.
	 */
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		System.out.println(">>>>> Before Server call<<<<<<");
		sendNameToServer();
		System.out.println(">>>>> Server call end <<<<<<");

	}

	/**
	 * Send the name from the nameField to the server and wait for a response.
	 */
	private void sendNameToServer() {

		String textToServer = "Hello Server";

		greetingService.greetServer(textToServer, new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				System.out.println("Remote Procedure Call - Failure");
			}

			public void onSuccess(String result) {
				System.out.println("Remote Procedure Call - sUCCESS");
			}
		});
	}

}
