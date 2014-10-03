package se.lnu.course4dv109.service.atomic;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import se.lnu.course4dv109.object.Bet;
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
		return requestOdds(event.getId());
	}

	@ServiceOperation
	public Odds requestOdds(int matchId) {
		if (!this.computeOdds.containsKey(matchId)) {
			this.computeOdds(matchId);
		}
		
		return this.computeOdds.get(matchId);
	}

	@ServiceOperation
	public void requestProfits(Bet bet) {
		
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
}