package com.mybank.xfire.example;

/** XFire sample interface. 
 * 
 */
public interface IBankingService {
	
	public String transferFunds(String fromAccount, String toAccount, double amount, String currency);

}