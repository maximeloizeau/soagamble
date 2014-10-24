package com.soa.client; 

import com.soa.service.composite.*;

import service.registry.ServiceRegistry;

public class Main {

	public static void main(String[] args) {
		ServiceRegistry.main(args);
		//BetService.main(args);
		//BankService.main(args);
		//OddsService.main(args);
		//SportsEventsService.main(args, this);
		BetCompositeService.main(args);
		//Client.main(args);
	}
}