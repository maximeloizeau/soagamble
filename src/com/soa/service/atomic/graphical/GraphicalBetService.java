package com.soa.service.atomic.graphical;

import java.text.DecimalFormat;

import com.soa.object.Bet;
import com.soa.object.Choice;
import com.soa.object.SportEvent;
import com.soa.service.atomic.BetService;
import com.webapp.client.event.State;
import com.webapp.server.WorkflowServiceImpl;

import service.auxiliary.ServiceOperation;

public class GraphicalBetService extends BetService {

	private WorkflowServiceImpl impl;

	public GraphicalBetService(String serviceName, String serviceEndpoint, WorkflowServiceImpl impl) {
		super(serviceName, serviceEndpoint);
		this.impl = impl;
	}

	@ServiceOperation
	public Bet placeBet(SportEvent event, int userId, Choice choice, double amount){
		Bet bet = super.placeBet(event, userId, choice, amount);
		String ch = this.getDecision(choice);
		DecimalFormat f = new DecimalFormat("#0.00"); 

		String[] tab = new String[4];
		tab[0] = event.getId().toString();
		tab[1] = f.format(amount) + " €";
		tab[2] = "on "+ch;
		tab[3] = "BetService.placeBet";
		impl.updateClientUI(tab, State.PLACE_BET);
		
		return bet;
	}

	private String getDecision(Choice choice) {
		String ch;
		
		switch(choice){
			case HOME_TEAM:
				ch="Home";
				break;
				
			case AWAY_TEAM:
				ch="Away";
				break;
				
			case DRAW:
				ch="Draw";
				break;
				
			default:ch="ND";
		}
		
		return ch;
	}
}