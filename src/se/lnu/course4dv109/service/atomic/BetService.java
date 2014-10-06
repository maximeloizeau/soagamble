package se.lnu.course4dv109.service.atomic;

import java.util.Map;

import se.lnu.course4dv109.object.Choice;
import service.atomic.AtomicService;
import service.auxiliary.ServiceOperation;

public class BetService extends AtomicService {
	
	public BetService(String serviceName, String serviceEndpoint) {
		super(serviceName, serviceEndpoint);
	}
	
	@ServiceOperation
	public boolean placeBet(String ccData, int matchId, Choice choice, double amount) {
		System.out.println("[4DV109] BetService.placeBet");
		return false;
	}
	
	
	public static void main(String[] args) {
		BetService betService = new BetService("BetService", "se.lnu.course4dv109.bet.service");
		
		Map<String, Object> customProperties = betService.getServiceDescription().getCustomProperties();
		customProperties.put("Cost", 2);
		customProperties.put("Complexity", 3);
		customProperties.put("ResponseTime", 5);
		betService.startService();
		betService.register();
	}
}
