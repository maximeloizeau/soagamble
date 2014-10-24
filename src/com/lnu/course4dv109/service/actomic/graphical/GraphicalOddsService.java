package com.lnu.course4dv109.service.actomic.graphical;

import com.lnu.soagamble2.client.event.State;
import com.lnu.soagamble2.server.WorkflowServiceImpl;

import se.lnu.course4dv109.object.Bet;
import se.lnu.course4dv109.object.Choice;
import se.lnu.course4dv109.object.Odds;
import se.lnu.course4dv109.object.SportEvent;
import se.lnu.course4dv109.service.atomic.OddsService;
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