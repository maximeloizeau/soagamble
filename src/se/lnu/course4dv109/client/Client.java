package se.lnu.course4dv109.client;

import java.util.List;

import se.lnu.course4dv109.object.Choice;
import service.composite.CompositeServiceClient;

public class Client {
	
	public static void main(String[] args) {
		CompositeServiceClient client = new CompositeServiceClient("se.lnu.course4dv109");
		List<String> qosRequirements = client.getQosRequirementNames();
		
		for(String qosRequirement: qosRequirements){
		    System.out.println("QoS requirement:" + qosRequirement );
		    boolean result = (boolean)client.invokeCompositeService(qosRequirement, "3333-3333", 1, Choice.DRAW, 10.0);
			System.out.print("[4DV109] Result: " + (result ? "true" : "false"));
		}
	}
}