package com.soa.service.atomic.graphical;

import com.soa.object.SportEvent;
import com.soa.service.atomic.SportsEventsService;
import com.webapp.client.event.State;
import com.webapp.server.WorkflowServiceImpl;

import service.auxiliary.ServiceOperation;

public class GraphicalSportsEventsService extends SportsEventsService {

	public GraphicalSportsEventsService(String serviceName, String serviceEndpoint, WorkflowServiceImpl impl) {
		super(serviceName, serviceEndpoint, impl);
	}
	
	@ServiceOperation
	public SportEvent[] getSportEvents(){
		SportEvent[] list = super.getSportEvents();
		String[] events = new String[list.length];
		for(int i=0;i<events.length;i++){
			events[i] = list[i].toString();
		}
		impl.updateClientUI(events, State.GET_SPORT_EVENTS);
		return list; 
	}
	
}