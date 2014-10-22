package se.lnu.course4dv109.client;

import java.util.List;

import se.lnu.course4dv109.object.Bet;
import se.lnu.course4dv109.object.Odds;
import service.composite.CompositeServiceClient;

public class Client {
	
	private static int userId = 0;
	
	public static void main(String[] args) {	
		CompositeServiceClient client = new CompositeServiceClient("se.lnu.course4dv109");

		List<String> qosRequirements = client.getQosRequirementNames();
		
		for(String qosRequirement: qosRequirements){
			userId++;
			Bet[] bets = new Bet[8];
			Odds[] odds = new Odds[8];
			
		    System.out.println("QoS requirement:" + qosRequirement );
		    client.invokeCompositeService(qosRequirement, "3333-3333", userId, odds, bets);
		}
	}
}