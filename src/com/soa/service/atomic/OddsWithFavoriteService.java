package com.soa.service.atomic;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.soa.object.Bet;
import com.soa.object.Choice;
import com.soa.object.Odds;
import com.soa.object.SportEvent;
import com.soa.service.atomic.graphical.GraphicalOddsWithFavoriteService;
import com.webapp.server.WorkflowServiceImpl;

import service.auxiliary.ServiceOperation;

public class OddsWithFavoriteService extends OddsService {

	Map<Integer, Odds> computeOdds = new HashMap<Integer, Odds>();
	private Random random = new Random();

	public OddsWithFavoriteService(String serviceName, String serviceEndpoint) {
		super(serviceName, serviceEndpoint);
	}

	@ServiceOperation
	public Odds requestOdds(SportEvent event) {
		System.out.println("[4DV109] OddsService.requestOdds");

		//if (!this.computeOdds.containsKey(event.getId())) {
		//this.computeOdds.clear();
		//this.computeOdds(event);
		//}

		return this.computeOdds(event);
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

	private Odds computeOdds(SportEvent match) {
		// Generate the percentage of winning of each odd
		int homeChance = random.nextInt(40) + 40;
		System.out.println("Homechance : "  + homeChance);
		int drawChance = random.nextInt(80 - homeChance) + 10;
		System.out.println("DrawChance : "  + drawChance);
		int awayChance = 100 - homeChance - drawChance;
		System.out.println("AwayChance : "  + awayChance);

		// Tweak the odds a little so it can be in favor of the booking system
		double home = 1/( ((double)homeChance) + random.nextInt(5) / 10) * 100;
		double draw = 1/( ((double)drawChance) + random.nextInt(5) / 10) * 100;
		double away = 1/( ((double)awayChance) + random.nextInt(5) / 10) * 100;

		home = Math.floor(home*100)/100;
		draw = Math.floor(draw*100)/100;
		away = Math.floor(away*100)/100;

		double[] oddsT = {home, draw, away};
		Arrays.sort(oddsT);

		Choice favorite = match.getFavorite();
		Odds odds;

		switch(favorite){
		case HOME_TEAM:
			odds = new Odds(oddsT[0], oddsT[1], oddsT[2]);
			break;
		case AWAY_TEAM:
			odds = new Odds(oddsT[1], oddsT[2], oddsT[0]);
			break;
		case DRAW:
			odds = new Odds(oddsT[2], oddsT[0], oddsT[1]);
			break;
		default:
			odds = new Odds(home, draw, away);
			break;
		}

		this.computeOdds.put(match.getId(), odds);
		
		return odds;
	}


	public static OddsService main(String[] args, WorkflowServiceImpl impl) {
		GraphicalOddsWithFavoriteService oddsService = new GraphicalOddsWithFavoriteService("OddsService", "se.lnu.course4dv109.service.oddswithfavorite", impl);

		Map<String, Object> customProperties = oddsService.getServiceDescription().getCustomProperties();
		customProperties.put("UseProviderFavorite", true);
		customProperties.put("Performance", 3);
		customProperties.put("DataReliability", true);
		oddsService.startService();
		oddsService.register();

		return oddsService;
	}

}