package com.soa.service.atomic.graphical;

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
		String ch;
		switch(choice){
		case HOME_TEAM:
			ch="Home team";
			break;
		case AWAY_TEAM:
			ch="Away team";
			break;
		case DRAW:
			ch="Draw";
			break;
		default:ch="ND";
		}
		String[] am = {""+amount+"€ on "+ch}; 
		impl.updateClientUI(am, State.PLACE_BET);
		return bet;
	}


}