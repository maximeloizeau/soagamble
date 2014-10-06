package se.lnu.course4dv109.service.composite;

import java.io.File;
import java.io.FileNotFoundException;

import se.lnu.course4dv109.qos.BestPerformanceQoS;
import service.composite.CompositeService;

public class BetCompositeService {
	public static void main(String[] args) throws FileNotFoundException {
		CompositeService compositeService = new CompositeService("SportsGambling", "se.lnu.course4dv109", new File("src/gamble-workflow.txt"));
		compositeService.addQosRequirement("BestPerformance", new BestPerformanceQoS());
		compositeService.startService();
		compositeService.register();
	}
}