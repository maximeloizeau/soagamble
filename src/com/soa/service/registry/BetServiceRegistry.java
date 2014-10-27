package com.soa.service.registry;

import service.registry.ServiceRegistry;

public class BetServiceRegistry extends ServiceRegistry {
	
	public static BetServiceRegistry start() {
		BetServiceRegistry serviceRegistry = new BetServiceRegistry();
		serviceRegistry.startService();
		
		return serviceRegistry;
	}

	public static void main(String[] args) {
		
	}
}