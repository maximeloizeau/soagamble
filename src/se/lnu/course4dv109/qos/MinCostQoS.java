package se.lnu.course4dv109.qos;

import java.util.HashMap;
import java.util.List;

import service.auxiliary.ServiceDescription;
import service.workflow.AbstractQoSRequirement;


public class MinCostQoS implements AbstractQoSRequirement {

    @Override
    public ServiceDescription applyQoSRequirement(List<ServiceDescription> serviceDescriptions) {
		int minCost = Integer.MAX_VALUE;
		int index = 0;
		
		for (int i = 0; i < serviceDescriptions.size(); i++) {
			HashMap<String, Object> properties = serviceDescriptions.get(i).getCustomProperties();
			
		    if (properties.containsKey("Cost")){
		    	int cost = (int)properties.get("Cost");
		    	
				if (cost < minCost){
				    minCost = cost;
				    index = i;
				}
		    }
		}
		
		return serviceDescriptions.get(index);
    }
}
