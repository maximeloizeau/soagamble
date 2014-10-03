package se.lnu.course4dv109.service.registry;

import service.atomic.AtomicService;

public class ServiceRegistry extends AtomicService {

	public ServiceRegistry() {
		super("SportsGamblingProviderRegistry", "ServiceRegistryEndpoint");
	}

	public static void main(String[] args) {
		ServiceRegistry serviceRegistry = new ServiceRegistry();
		serviceRegistry.startService();
	}
}