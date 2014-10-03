package se.lnu.course4dv109.service.composite;

import java.io.File;

import se.lnu.course4dv109.object.Choice;
import service.auxiliary.ServiceOperation;
import service.composite.CompositeService;

public class BetCompositeService {
	public static void main(String[] args) {
		CompositeService compositeService = new CompositeService("SportsGambling", "se.lnu.course4dv109", new File("src/gamble-workflow.txt"));
		//compositeService.addQosRequirement("MinCost", new MinCostQoS());
		compositeService.startService();
		compositeService.register();
	}
	
	@ServiceOperation
	public boolean placeBet(String ccData, int matchId, Choice choice, double amount) {
		return false;
	}
	
	
}