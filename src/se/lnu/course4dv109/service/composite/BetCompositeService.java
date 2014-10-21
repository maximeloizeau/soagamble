package se.lnu.course4dv109.service.composite;

import se.lnu.course4dv109.qos.BestPerformanceQoS;
import service.composite.CompositeService;

public class BetCompositeService {
	public static void main(String[] args) {
		CompositeService compositeService = new CompositeService("SportsGambling", "se.lnu.course4dv109", "src/gamble-workflow.txt");
		//compositeService.addQosRequirement("BestPerformance", new BestPerformanceQoS());
		compositeService.startService();
		compositeService.register();
	}
}