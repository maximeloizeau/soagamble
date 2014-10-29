package com.soa.service.composite;

import java.util.Random;

import com.soa.object.Choice;
import com.soa.object.Odds;
import com.soa.object.SportEvent;
import com.soa.qos.BestPerformanceQoS;
import com.soa.qos.UseProviderFavoriteQoS;
import com.soa.service.atomic.graphical.GraphicalBetCompositeService;
import com.webapp.server.WorkflowServiceImpl;

import service.auxiliary.LocalOperation;
import service.composite.CompositeService;

public class BetCompositeService extends CompositeService {
		
	private double amount = 0;
	private String pathToWorkflow;
	
	public static BetCompositeService main(String[] args, WorkflowServiceImpl impl) {
		BetCompositeService compositeService = new GraphicalBetCompositeService(args[0], impl);
		compositeService.start();
		
		return compositeService;
	}
	
	public BetCompositeService(String path) {
		super("SportsGambling", "se.lnu.course4dv109", path);
		this.pathToWorkflow = path;
	}
	
	public void start() {
		this.addQosRequirement("BestPerformance", new BestPerformanceQoS());
		this.addQosRequirement("UseProviderFavorite", new UseProviderFavoriteQoS());
		this.startService();
		this.register();
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
		System.out.println("reset total");
	}
	
	@LocalOperation
	public Choice getChoice(SportEvent event, Odds odds) {
		double min = 1000.0;
		Choice saferChoice = null;
		for(Choice c : Choice.values()) {
			if(odds.getOdds(c) < min) {
				min = odds.getOdds(c);
				saferChoice = c;
			}
		}
		
		/*
		 * User had 75% chance of chosing the safer bet (lowest odd) 
		 */
		int rand = new Random().nextInt(100);
		if(rand < 75) {
			return saferChoice;
		} else if(rand%2 == 0) {
			return getOtherResults(saferChoice)[0];
		} else {
			return getOtherResults(saferChoice)[1];
		}
	}
	
	private Choice[] getOtherResults(Choice choice){
		Choice[] c = new Choice[2];
		switch(choice){
		case HOME_TEAM:
			c[0] = Choice.DRAW;
			c[1] = Choice.AWAY_TEAM;
			return c ;
		case AWAY_TEAM:
			c[0] = Choice.HOME_TEAM;
			c[1] = Choice.DRAW;
			return c ;
		case DRAW:
			c[0] = Choice.AWAY_TEAM;
			c[1] = Choice.HOME_TEAM;
			return c ;
		default:
			return null;
		}
	}
	
	private double getRandom(int max) {
		return Math.round((new Random()).nextInt(max * 10000) / 100.0) / 100.0;
	}
}