package se.lnu.course4dv109.service.registry;

import service.atomic.AtomicService;

public class ServiceRegistry extends AtomicService {

	public ServiceRegistry() {
		super("SportsGamblingProviderRegistry", "se.lnu.course4dv109");
	}

	public static void main(String[] args) {
		ServiceRegistry serviceRegistry = new ServiceRegistry();
		serviceRegistry.startService();
	}
}