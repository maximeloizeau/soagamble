package se.lnu.course4dv109.service.registry;

import service.registry.ServiceRegistry;

public class BetServiceRegistry extends ServiceRegistry {

	public static void main(String[] args) {
		BetServiceRegistry serviceRegistry = new BetServiceRegistry();
		serviceRegistry.startService();
	}
}