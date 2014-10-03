package se.lnu.course4dv109.qos;

import java.util.LinkedList;
import java.util.List;

import service.auxiliary.ServiceDescription;
import service.workflow.AbstractQoSRequirement;


public class MinCostAndResponseTimeQoS implements AbstractQoSRequirement {

    @Override
    public ServiceDescription applyQoSRequirement(List<ServiceDescription> serviceDescriptions) {
		int minCost = Integer.MAX_VALUE;
	
		// find min cost
		for (int i = 0; i < serviceDescriptions.size(); i++) {
		    if (serviceDescriptions.get(i).getCustomProperties().containsKey("Cost")){
				int cost = (int) serviceDescriptions.get(i).getCustomProperties().get("Cost");
				
				if (cost < minCost){
				    minCost = cost;
				}
		    }
		}
		
		// create list of services that has mincost
		List<ServiceDescription> minCostDescriptions = new LinkedList<ServiceDescription>();
		
		for(ServiceDescription serviceDescription: serviceDescriptions){
		    if ((int) serviceDescription.getCustomProperties().get("Cost") == minCost){
		    	minCostDescriptions.add(serviceDescription);
		    }
		}
		
		int minResponseTime = Integer.MAX_VALUE, responseTime, index = 0;
		
		// find min response time
		for (int i = 0; i < minCostDescriptions.size(); i++) {
		    if (minCostDescriptions.get(i).getCustomProperties().containsKey("ResponseTime")){
				responseTime = (int) minCostDescriptions.get(i).getCustomProperties().get("ResponseTime");
				
				if (responseTime < minResponseTime){
				    minResponseTime = responseTime;
				    index = i;
				}
		    }
		}
		
		return minCostDescriptions.get(index);	
    }
}
