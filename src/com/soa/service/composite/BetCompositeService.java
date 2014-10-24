package com.soa.service.composite;

import java.util.Random;

import com.soa.object.Bet;
import com.soa.object.Choice;
import com.soa.object.Odds;
import com.soa.object.SportEvent;
import com.soa.qos.BestPerformanceQoS;

import service.auxiliary.LocalOperation;
import service.composite.CompositeService;

public class BetCompositeService extends CompositeService {
		
	private double amount = 0;
	private String pathToWorkflow;
	
	public static void main(String[] args) {
		BetCompositeService bcs = new BetCompositeService(args[0]);
		bcs.start();
	}
	
	public BetCompositeService(String path) {
		super("SportsGambling", "se.lnu.course4dv109", path);
		this.pathToWorkflow = path;
	}
	
	public void start() {
		BetCompositeService compositeService = new BetCompositeService(pathToWorkflow);
		compositeService.addQosRequirement("BestPerformance", new BestPerformanceQoS());
		compositeService.startService();
		compositeService.register();
	}
	
	@LocalOperation
	public void printEvents(SportEvent[] events) {
		System.out.println("[4DV109] Matches: ");
		
		for (int i=0; i < events.length; i++) {
			System.out.println("  " + events[i]);
		}
	}
	
	@LocalOperation
	public void printOdds(SportEvent[] events, Odds[] odds) {
		System.out.println("[4DV109] Odds: ");
		
		for (int i=0; i < events.length; i++) {
			System.out.println("  " + events[i]);
			System.out.println("      " + odds[i] + "\n");
		}
	}
	
	@LocalOperation
	public void printBets(SportEvent[] events, Bet[] bets, Odds[] odds) {
		System.out.println("[4DV109] Bets: ");
		
		for (int i=0; i < events.length; i++) {
			Choice c = bets[i].getChoice();
			
			System.out.println("  " + events[i]);
			System.out.println("      " + bets[i] + " (Odds: " + odds[i].getOdds(c) + ")\n");
		}
	}
	
	@LocalOperation
	public double getAmount(SportEvent event) {
		double rand = this.getRandom(15);
		amount += rand;
		
		return rand;
	}
	
	@LocalOperation
	public double getTotalAmount() {
		return this.amount;
	}
	
	@LocalOperation
	public void resetTotalAmount() {
		this.amount=0;
	}
	
	@LocalOperation
	public Choice getChoice(SportEvent event) {
		Double rand = this.getRandom(100000) % 3;
		
		switch (rand.intValue()) {
			case 0:
				return Choice.HOME_TEAM;
				
			case 1:
				return Choice.DRAW;
				
			case 2:
				return Choice.AWAY_TEAM;
		}
		
		return null;
	}
	
	private double getRandom(int max) {
		return Math.round((new Random()).nextInt(max * 10000) / 100.0) / 100.0;
	}
}