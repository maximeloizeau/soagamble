package com.soa.qos;

import java.util.HashMap;
import java.util.List;

import service.auxiliary.ServiceDescription;
import service.workflow.AbstractQoSRequirement;


public class UseProviderFavoriteQoS implements AbstractQoSRequirement {

    @Override
    public ServiceDescription applyQoSRequirement(List<ServiceDescription> serviceDescriptions) {		
		for (int i = 0; i < serviceDescriptions.size(); i++) {
			HashMap<String, Object> properties = serviceDescriptions.get(i).getCustomProperties();
			
		    if (properties.containsKey("UseProviderFavorite")){
		    	boolean fav = (boolean)properties.get("UseProviderFavorite");
		    	
				if (fav){
					return serviceDescriptions.get(i);
				}
		    }
		}
		int min = Integer.MAX_VALUE;
		int index = 0;
		
		for (int i = 0; i < serviceDescriptions.size(); i++) {
			HashMap<String, Object> properties = serviceDescriptions.get(i).getCustomProperties();
			
		    if (properties.containsKey("Performance")){
		    	int performance = (int)properties.get("Performance");
		    	
				if (performance < min){
				    min = performance;
				    index = i;
				}
		    }
		}
		
		return serviceDescriptions.get(index);
    }
}
