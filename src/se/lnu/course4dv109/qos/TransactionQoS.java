package se.lnu.course4dv109.qos;

import java.util.HashMap;
import java.util.List;

import service.auxiliary.ServiceDescription;
import service.workflow.AbstractQoSRequirement;


public class TransactionQoS implements AbstractQoSRequirement {

    @Override
    public ServiceDescription applyQoSRequirement(List<ServiceDescription> serviceDescriptions) {		
		for (int i = 0; i < serviceDescriptions.size(); i++) {
			HashMap<String, Object> properties = serviceDescriptions.get(i).getCustomProperties();
			
		    if (properties.containsKey("DataReliability")){
		    	boolean trans = (boolean)properties.get("DataReliability");
		    	
				if (trans){
					return serviceDescriptions.get(i);
				}
		    }
		}
		
		return null;
    }
}
