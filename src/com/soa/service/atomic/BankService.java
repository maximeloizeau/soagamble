package com.soa.service.atomic;

import java.text.DecimalFormat;
import java.util.Map;

import com.soa.service.atomic.graphical.GraphicalBankService;
import com.webapp.server.WorkflowServiceImpl;

import service.atomic.AtomicService;
import service.auxiliary.ServiceOperation;

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
	@ServiceOperation
	public boolean makePayment(String ccData, double amount) {
		System.out.println("[4DV109] BankService proceed to payment : "+ccData+", "+amount+" unit of money to the system");
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
		System.out.println("[4DV109] BankService proceed to payment : "+ccData+", "+amount+" unit of money to the customer");
		return true;
	}
	
	@ServiceOperation
	public double requestPayment(String ccData, double[] amount) {
		double sum = 0.0;
		
		for (double s : amount) {
			sum += s;
		}
		
		DecimalFormat d = new DecimalFormat("#.##");
		
		System.out.println("[4DV109] BankService proceed to payment : "+ccData+", "+d.format(sum)+" unit of money to the customer");
		return sum;
	}
	
	
	public static void main(String[] args, WorkflowServiceImpl impl) {
		GraphicalBankService bankService = new GraphicalBankService("BankService", "se.lnu.course4dv109.service.bank", impl);
		
		Map<String, Object> customProperties = bankService.getServiceDescription().getCustomProperties();
		customProperties.put("Transaction", true);
		bankService.startService();
		bankService.register();
	}
}
