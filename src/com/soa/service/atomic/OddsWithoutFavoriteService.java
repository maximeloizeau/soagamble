package com.soa.service.atomic;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.soa.object.Bet;
import com.soa.object.Choice;
import com.soa.object.Odds;
import com.soa.object.SportEvent;
import com.soa.service.atomic.graphical.GraphicalOddsWithoutFavoriteService;
import com.webapp.server.WorkflowServiceImpl;

import service.atomic.AtomicService;
import service.auxiliary.ServiceOperation;

public class OddsWithoutFavoriteService extends OddsService {

	Map<Integer, Odds> computeOdds = new HashMap<Integer, Odds>();
	private Random random = new Random();

	public OddsWithoutFavoriteService(String serviceName, String serviceEndpoint) {
		super(serviceName, serviceEndpoint);
	}

	@ServiceOperation
	public Odds requestOdds(SportEvent event) {
		System.out.println("[4DV109] OddsService.requestOdds");

		//if (!this.computeOdds.containsKey(event.getId())) {
		//	this.computeOdds(event);
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
		int homeChance = random.nextInt(30) + 60;
		System.out.println("Homechance : "  + homeChance);
		int drawChance = random.nextInt(95 - homeChance) + 5;
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
		
		Odds odds = new Odds(home, draw, away);
		this.computeOdds.put(match.getId(), odds);
		
		return odds;
	}


	public static OddsService main(String[] args, WorkflowServiceImpl impl) {
		GraphicalOddsWithoutFavoriteService oddsService = new GraphicalOddsWithoutFavoriteService("OddsService", "se.lnu.course4dv109.service.oddswithoutfavorite", impl);

		Map<String, Object> customProperties = oddsService.getServiceDescription().getCustomProperties();
		customProperties.put("UseProviderFavorite", false);
		customProperties.put("Performance", 2);
		customProperties.put("DataReliability", true);
		oddsService.startService();
		oddsService.register();

		return oddsService;
	}

}