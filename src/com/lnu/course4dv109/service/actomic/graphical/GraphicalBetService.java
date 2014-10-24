package com.lnu.course4dv109.service.actomic.graphical;

import com.lnu.soagamble2.client.event.State;
import com.lnu.soagamble2.server.WorkflowServiceImpl;

import se.lnu.course4dv109.object.Bet;
import se.lnu.course4dv109.object.Choice;
import se.lnu.course4dv109.object.SportEvent;
import se.lnu.course4dv109.service.atomic.BetService;
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