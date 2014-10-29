package com.soa.service.atomic.graphical;

import java.text.DecimalFormat;

import com.soa.object.Bet;
import com.soa.object.Choice;
import com.soa.object.Odds;
import com.soa.object.SportEvent;
import com.soa.service.atomic.OddsWithoutFavoriteService;
import com.webapp.client.event.State;
import com.webapp.server.WorkflowServiceImpl;

import service.auxiliary.ServiceOperation;

public class GraphicalOddsWithoutFavoriteService extends OddsWithoutFavoriteService {

	private WorkflowServiceImpl impl;
	
	public GraphicalOddsWithoutFavoriteService(String serviceName, String serviceEndpoint, WorkflowServiceImpl impl) {
		super(serviceName, serviceEndpoint);
		this.impl = impl;
	}

	@ServiceOperation
	public Odds requestOdds(SportEvent match){
		Odds odds = super.requestOdds(match);
		
		DecimalFormat f = new DecimalFormat("#0.00"); 
		
		String[] tab = new String[5];
		tab[0] = match.getId().toString();
		tab[1] = f.format(odds.getOdds(Choice.HOME_TEAM));
		tab[2] = f.format(odds.getOdds(Choice.DRAW));
		tab[3] = f.format(odds.getOdds(Choice.AWAY_TEAM));
		tab[4] = "OddsService.requestOdds";
		
		impl.updateClientUI(tab, State.REQUEST_ODDS);
		
		return odds ;
	}

	@ServiceOperation
	public double requestProfits(SportEvent event, Bet bet) {
		double profit = super.requestProfits(event, bet);
		
		DecimalFormat f = new DecimalFormat("#0.00"); 
		
		String[] tab = new String[4];
		tab[0] = event.getId().toString();
		tab[1] = this.getResult(event.getResult());
		tab[2] = profit == 0 ? "-" : f.format(profit) + " €";
		tab[3] = "OddsService.requestProfits";
		impl.updateClientUI(tab, State.REQUEST_PROFIT);
		return profit;		
	}

	private String getResult(Choice choice) {
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