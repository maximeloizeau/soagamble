package com.soa.service.registry;

import service.registry.ServiceRegistry;

public class BetServiceRegistry extends ServiceRegistry {

	public static BetServiceRegistry main(String[] args) {
		BetServiceRegistry serviceRegistry = new BetServiceRegistry();
		serviceRegistry.startService();
		
		return serviceRegistry;
	}
}