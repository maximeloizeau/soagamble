package com.soa.service.atomic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.soa.object.Bet;
import com.soa.object.Choice;
import com.soa.object.SportEvent;
import com.soa.service.atomic.graphical.GraphicalBetService;
import com.webapp.server.WorkflowServiceImpl;

import service.atomic.AtomicService;
import service.auxiliary.ServiceOperation;

public class BetService extends AtomicService {

	Map<Integer, List<Bet>> bets = new HashMap<Integer, List<Bet>>();
	
	public BetService(String serviceName, String serviceEndpoint) {
		super(serviceName, serviceEndpoint);
	}
	
	@ServiceOperation
	public Bet placeBet(SportEvent event, int userId, Choice choice, double amount) {
		System.out.println("[4DV109] BetService.placeBet");
		System.out.println("             Beting: " + choice.toString());
		System.out.println("             Amount: " + amount + " €");
		
		Bet bet = new Bet(choice, amount, event.getId());
		List<Bet> list = getBets(userId);
		list.add(bet);
		
		bets.put(userId, list);
		
		return bet;
	}
	
	private List<Bet> getBets(int userId) {
		List<Bet> list = this.bets.get(userId);
		
		if (list == null) {
			list = new ArrayList<Bet>();
		}
		
		return list;
	}
	
	public static void main(String[] args, WorkflowServiceImpl impl) {
		GraphicalBetService betService = new GraphicalBetService("BetService", "se.lnu.course4dv109.service.bet", impl);
		
		Map<String, Object> customProperties = betService.getServiceDescription().getCustomProperties();
		customProperties.put("Performance", 1);
		
		betService.startService();
		betService.register();
	}
}