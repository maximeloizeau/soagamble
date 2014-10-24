package com.soa.service.atomic.graphical;

import com.soa.object.Bet;
import com.soa.object.Choice;
import com.soa.object.Odds;
import com.soa.object.SportEvent;
import com.soa.service.atomic.OddsService;
import com.webapp.client.event.State;
import com.webapp.server.WorkflowServiceImpl;

import service.auxiliary.ServiceOperation;

public class GraphicalOddsService extends OddsService {

	private WorkflowServiceImpl impl;
	
	public GraphicalOddsService(String serviceName, String serviceEndpoint, WorkflowServiceImpl impl) {
		super(serviceName, serviceEndpoint);
		this.impl = impl;
	}

	@ServiceOperation
	public Odds requestOdds(SportEvent match){
		Odds odds = super.requestOdds(match);
		String[] tab = new String[3];
		tab[0] = ""+odds.getOdds(Choice.HOME_TEAM);
		tab[1] = ""+odds.getOdds(Choice.DRAW);
		tab[2] = ""+odds.getOdds(Choice.AWAY_TEAM);
		impl.updateClientUI(tab, State.REQUEST_ODDS);
		return odds ;
	}

	@ServiceOperation
	public double requestProfits(SportEvent event, Bet bet) {
		double profit = super.requestProfits(event, bet);
		String[] data = {""+profit};
		impl.updateClientUI(data, State.REQUEST_PROFIT);
		return profit;
		
	}
}