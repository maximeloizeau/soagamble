package se.lnu.course4dv109.service.atomic;

import service.atomic.AtomicService;

/**
 * 
 * This service realizes every transaction dealing with money.
 * Basically it allows to transfer money between the customer and the system.
 *
 */
public class BankService extends AtomicService {

	public BankService(String serviceName, String serviceEndpoint) {
		super(serviceName, serviceEndpoint);
	}

	/**
	 * Transfers money from the client to the system. 
	 * For example used to place money on a team for a sport event.
	 * @param ccData : Credit Card info of the customer.
	 * @param amount : the amount of money
	 * @return true if it was successful, false if not.
	 */
	public boolean makePayment(String ccData, double amount) {
		System.out.println("BankService proceed to payment : "+ccData+", "+amount+" unit of money to the system");
		return true;
	}
	
	/**
	 * Transfer money from the system to the customer. 
	 * For example used to pay the customer when he wins. 
	 * @param ccData : Credit card info of the user
	 * @param amount : the amount of money to pay
	 * @return true if the payment was made, false if not.
	 */
	public boolean requestPayment(String ccData, double amount) {
		System.out.println("BankService proceed to payment : "+ccData+", "+amount+" unit of money to the customer");
		return true;
	}
}
