package se.lnu.course4dv109.service.atomic;

import java.util.Map;

import se.lnu.course4dv109.object.Bet;
import se.lnu.course4dv109.object.Choice;
import se.lnu.course4dv109.object.SportEvent;
import service.atomic.AtomicService;
import service.auxiliary.ServiceOperation;

public class BetService extends AtomicService {
	
	public BetService(String serviceName, String serviceEndpoint) {
		super(serviceName, serviceEndpoint);
	}
	
	@ServiceOperation
	public Bet placeBet(SportEvent event, Choice choice, double amount) {
		System.out.println("[4DV109] BetService.placeBet");
		
		Bet bet = new Bet(choice, amount);
		event.addBet(bet);
		
		return bet;
	}
	
	
	public static void main(String[] args) {
		BetService betService = new BetService("BetService", "se.lnu.course4dv109.service.bet");
		
		Map<String, Object> customProperties = betService.getServiceDescription().getCustomProperties();
		customProperties.put("Cost", 2);
		customProperties.put("Complexity", 3);
		customProperties.put("ResponseTime", 5);
		
		betService.startService();
		betService.register();
	}
}
