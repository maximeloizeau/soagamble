package se.lnu.course4dv109.service.composite;

import java.io.File;

import se.lnu.course4dv109.qos.MinCostQoS;
import service.composite.CompositeService;

public class BetCompositeService {
	public static void main(String[] args) {
		CompositeService compositeService = new CompositeService("SportsGambling", "se.lnu.course4dv109", new File("src/gamble-workflow.txt"));
		compositeService.addQosRequirement("MinCost", new MinCostQoS());
		compositeService.startService();
		compositeService.register();
	}
}