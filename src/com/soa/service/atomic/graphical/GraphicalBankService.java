package com.soa.service.atomic.graphical;

import com.soa.service.atomic.BankService;
import com.webapp.client.event.State;
import com.webapp.server.WorkflowServiceImpl;

import service.auxiliary.ServiceOperation;

/**
 * 
 * This service realizes every transaction dealing with money.
 * Basically it allows to transfer money between the customer and the system.
 *
 */
public class GraphicalBankService extends BankService {
	private WorkflowServiceImpl impl;
	
	public GraphicalBankService(String serviceName, String serviceEndpoint, WorkflowServiceImpl impl) {
		super(serviceName, serviceEndpoint);
		this.impl = impl;
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
		String[] tab = {""+amount, "BankService.makePayment"};
		impl.updateClientUI(tab, State.MAKE_PAYMENT);
		return super.makePayment(ccData, amount);
	}
	
	@ServiceOperation
	public double requestPayment(String ccData, double[] amount) {
		String[] tab = {"BankService.requestPayment"};
		impl.updateClientUI(tab, State.LOCAL_OPERATION);
		return super.requestPayment(ccData, amount);
	}
}
