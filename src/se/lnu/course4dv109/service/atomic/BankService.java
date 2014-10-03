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
	
	
	public static void main(String[] args) {
		BankService bankService = new BankService("BankService", "se.lnu.course4dv109.service.bank");
		
//		HashMap customProperties = matrix.getServiceDescription().getCustomProperties();
//		customProperties.put("Cost", 2);
//		customProperties.put("Complexity", 3);
//		customProperties.put("ResponseTime", 5);
		bankService.startService();
		bankService.register();
	}
}
