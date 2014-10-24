package se.lnu.course4dv109.service.atomic;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.lnu.course4dv109.service.actomic.graphical.GraphicalOddsService;
import com.lnu.soagamble2.server.WorkflowServiceImpl;

import se.lnu.course4dv109.object.Bet;
import se.lnu.course4dv109.object.Choice;
import se.lnu.course4dv109.object.Odds;
import se.lnu.course4dv109.object.SportEvent;
import service.atomic.AtomicService;
import service.auxiliary.ServiceOperation;

public class OddsService extends AtomicService {

	Map<Integer, Odds> computeOdds = new HashMap<Integer, Odds>();
	private Random random = new Random();
	
	public OddsService(String serviceName, String serviceEndpoint) {
		super(serviceName, serviceEndpoint);
	}

	@ServiceOperation
	public Odds requestOdds(SportEvent event) { 		
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
			money = bet.getAmount()*this.computeOdds.get(event.getId()).getOdds(choice);
		}
		
		System.out.println("[4DV109] OddsService.requestProfits");
		System.out.println("             Choice: " + choice.toString());
		System.out.println("             Result: " + result.toString());
		System.out.println("             You Won: " + money + " €");
		
		return Math.floor(money * 100) / 100;
	}
	
	private void computeOdds(int matchId) {
		double home = this.getRandom();
		double draw = this.getRandom();
		double away = this.getRandom();
		
		Odds odds = new Odds(home, draw, away);
		
		this.computeOdds.put(matchId, odds);
	}	
	
	private double getRandom() {
		double val = random.nextInt(100);
		return (val / 10.0)+1;
	}
	
	
	public static void main(String[] args, WorkflowServiceImpl impl) {
		GraphicalOddsService oddsService = new GraphicalOddsService("OddsService", "se.lnu.course4dv109.service.odds", impl);
		
		Map<String, Object> customProperties = oddsService.getServiceDescription().getCustomProperties();
		customProperties.put("Performance", 2);
		customProperties.put("DataReliability", true);
		oddsService.startService();
		oddsService.register();
	}

}