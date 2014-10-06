package se.lnu.course4dv109.qos;

import java.util.List;

import service.auxiliary.ServiceDescription;
import service.workflow.AbstractQoSRequirement;

public class MinResponseTimeQoS implements AbstractQoSRequirement {

    @Override
    public ServiceDescription applyQoSRequirement(List<ServiceDescription> serviceDescriptions) {
		int minResponseTime = Integer.MAX_VALUE;
		int index = 0;
		
		for (int i = 0; i < serviceDescriptions.size(); i++) {
		    if (serviceDescriptions.get(i).getCustomProperties().containsKey("ResponseTime")){
				int responseTime = (int) serviceDescriptions.get(i).getCustomProperties().get("ResponseTime");
				
				if (responseTime < minResponseTime){
				    minResponseTime = responseTime;
				    index = i;
				}
		    } else {
		    	throw new RuntimeException("response_time custom property not found in service description.");
		    }
		}
		
		return serviceDescriptions.get(index);
    }
}