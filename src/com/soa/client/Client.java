package com.soa.client;

import com.soa.object.Bet;
import com.soa.object.Odds;

import service.composite.CompositeServiceClient;

public class Client extends CompositeServiceClient {

	private static int userId = 0;

	public Client() {
		super("se.lnu.course4dv109");
	}
	public double start(boolean favorite, double availableMoney) {	
		userId++;

		qosRequirements.add("BestPerformance");
		//List<String> qosRequirements = new ArrayList<String>(); //client.getQosRequirementNames();
		String qosRequirement;
		
		    result = (double)this.invokeCompositeService(qosRequirement, "3333-3333", userId, odds, bets, profits, result, availableMoney);
		if(favorite){
			qosRequirement = "UseProviderFavorite";
		}
		else{
			qosRequirement = "BestPerformance";
		}

		Bet[] bets = new Bet[8];
		Odds[] odds = new Odds[8];
		double[] profits = new double[8];
		double result = 0.0;

		result = (double)this.invokeCompositeService(qosRequirement, "3333-3333", userId, odds, bets, profits, result);

		//Here we return because we only want to run once for the moment. Remove the return and it will run for each QoS requirement
		return result;
	}
}