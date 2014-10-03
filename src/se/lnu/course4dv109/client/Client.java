package se.lnu.course4dv109.client;

import se.lnu.course4dv109.service.composite.BetCompositeService;
import service.composite.CompositeService;

public class Client {
	
	public static void main(String[] args) {
		CompositeService client = new CompositeService("se.lnu.course4dv109");
		
		int a[][] = {initialize matrix};
		int b[][] = {initialize matrix};
		
		String qosRequirements =“MinCost”;
		
		Object result = client.invokeCompositeService(qosRequirement, a, b);
	}
}