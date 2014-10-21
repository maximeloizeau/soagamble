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

	// @ServiceOperation
	public Odds requestOdds(int matchId) {
		System.out.println("[4DV109] OddsService.requestOdds");
		
		if (!this.computeOdds.containsKey(matchId)) {
			this.computeOdds(matchId);
		}
		
		return this.computeOdds.get(matchId);
	}

	@ServiceOperation
	public double requestProfits(SportEvent event, Bet bet) {		
		Choice result = event.getResult();
		Choice choice = bet.getChoice();
		double money = 0.0;
		
		if (result.equals(choice)) {
			money = this.computeOdds.get(event.getId()).getOdds(choice);
		}
		
		System.out.println("[4DV109] OddsService.requestProfits");
		System.out.println("             Choice: " + choice.toString());
		System.out.println("             Result: " + result.toString());
		System.out.println("             You Won: " + money + " €");
		
		return money;
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
		
		//Map<String, Object> customProperties = oddsService.getServiceDescription().getCustomProperties();
		//customProperties.put("Performance", 2);
		//customProperties.put("DataReliability", true);
		oddsService.startService();
		oddsService.register();
	}

}