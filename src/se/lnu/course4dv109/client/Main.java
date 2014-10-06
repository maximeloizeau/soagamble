package se.lnu.course4dv109.client;

import java.io.FileNotFoundException;

import se.lnu.course4dv109.service.atomic.*;
import se.lnu.course4dv109.service.composite.*;
import service.registry.ServiceRegistry;

public class Main {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		ServiceRegistry.main(args);
		BetService.main(args);
		BankService.main(args);
		OddsService.main(args);
		SportsEventsService.main(args);
		BetCompositeService.main(args);
		Client.main(args);
	}

}
