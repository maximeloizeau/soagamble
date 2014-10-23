package se.lnu.course4dv109.client; 

import se.lnu.course4dv109.service.atomic.*;
import se.lnu.course4dv109.service.composite.*;
import service.registry.ServiceRegistry;

public class Main {

	public static void main(String[] args) {
		ServiceRegistry.main(args);
		BetService.main(args);
		BankService.main(args);
		OddsService.main(args);
		//SportsEventsService.main(args, this);
		BetCompositeService.main(args);
		//Client.main(args);
	}
}