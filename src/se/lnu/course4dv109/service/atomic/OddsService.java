package se.lnu.course4dv109.service.atomic;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import se.lnu.course4dv109.object.Bet;
import se.lnu.course4dv109.object.Choice;
import se.lnu.course4dv109.object.Odds;
import se.lnu.course4dv109.object.SportEvent;
import service.atomic.AtomicService;
import service.auxiliary.ServiceOperation;

public class OddsService extends AtomicService {

	Map<Integer, Odds> computeOdds = new HashMap<Integer, Odds>();
	
	public OddsService(String serviceName, String serviceEndpoint) {
		super(serviceName, serviceEndpoint);
	}

	@ServiceOperation
	public Odds requestOdds(SportEvent event) {
		System.out.println("[4DV109] requestOdds.placeBet");
		
		return requestOdds(event.getId());
	}

	@ServiceOperation
	public Odds requestOdds(int matchId) {
		System.out.println("[4DV109] OddsService.requestOdds");
		
		if (!this.computeOdds.containsKey(matchId)) {
			this.computeOdds(matchId);
		}
		
		return this.computeOdds.get(matchId);
	}

	@ServiceOperation
	public double requestProfits(SportEvent event, Bet bet) {
		System.out.println("[4DV109] OddsService.requestProfits");
		
		Choice result = event.getResult();
		Choice choice = bet.getChoice();
		
		if (result.equals(choice)) {
			return this.computeOdds.get(event.getId()).getOdds(choice);
		}
		
		return 0.0;
	}
	
	private void computeOdds(int matchId) {
		double home = this.getRandom();
		double draw = this.getRandom();
		double away = this.getRandom();
		
		Odds odds = new Odds(home, draw, away);
		
		this.computeOdds.put(matchId, odds);
	}	
	
	private double getRandom() {
		return (new Random()).nextInt(100) / 100;
	}
	
	
	public static void main(String[] args) {
		OddsService oddsService = new OddsService("OddsService", "se.lnu.course4dv109.service.odds");
		
//		HashMap customProperties = matrix.getServiceDescription().getCustomProperties();
//		customProperties.put("Cost", 2);
//		customProperties.put("Complexity", 3);
//		customProperties.put("ResponseTime", 5);
		oddsService.startService();
		oddsService.register();
	}

}