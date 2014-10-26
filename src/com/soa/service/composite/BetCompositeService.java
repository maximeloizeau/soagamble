package com.soa.service.composite;

import java.util.Random;

import com.soa.object.Bet;
import com.soa.object.Choice;
import com.soa.object.Odds;
import com.soa.object.SportEvent;
import com.soa.qos.BestPerformanceQoS;
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
		System.out.println("reset t otal");
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