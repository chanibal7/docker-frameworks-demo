package com.mybank.xfire.example;

import java.text.NumberFormat;
import java.text.DecimalFormat;

/** XFire WebServices sample implementation class. 
 */
public class BankingService implements IBankingService {
	
	//default constructor
	public BankingService(){	
	}
	
	/** Transfers fund from one account to another.
	*/
	public String transferFunds(
		String fromAccount, String toAccount, double amount, String currency){
		String statusMessage = "";				
		//Call business objects and other components to get the job done.
		//Then create a status message and return.
		try {
			NumberFormat formatter = new DecimalFormat("###,###,###,###.00");		
	        statusMessage = "COMPLETED: " + currency + " " + formatter.format(amount)+ 
	        " was successfully transferred from A/C# " + fromAccount + " to A/C# " + toAccount;
		} catch (Exception e){
			statusMessage = "BankingService.transferFunds(): EXCEPTION: " + e.toString();
		}
		return statusMessage;
	}
	
}