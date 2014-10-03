package se.lnu.course4dv109.client;

import service.composite.CompositeServiceClient;

public class Client {
	
	public static void main(String[] args) {
		CompositeServiceClient client = new CompositeServiceClient("se.lnu.course4dv109");
				
		Object result = client.invokeCompositeService("placeBet");
	}
}