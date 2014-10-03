package se.lnu.course4dv109.service.atomic;

import service.atomic.AtomicService;

public class BankService extends AtomicService {

	public BankService(String serviceName, String serviceEndpoint) {
		super(serviceName, serviceEndpoint);
	}

	public boolean makePayment(String ccData, double amount) {
		return true;
	}
	
	public boolean requestPayment(String ccData, double amount) {
		return true;
	}
}
