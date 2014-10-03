package se.lnu.course4dv109.client;

import se.lnu.course4dv109.object.Choice;
import service.composite.CompositeServiceClient;

public class Client {
	
	public static void main(String[] args) {
		CompositeServiceClient client = new CompositeServiceClient("se.lnu.course4dv109");
				
		Object result = client.invokeCompositeService("", "ccc", 1, Choice.AWAY_TEAM, 12.50);
		System.out.println(result);
	}
}