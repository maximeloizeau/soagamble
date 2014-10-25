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
		String[] events = new String[list.length+1];
		
		for (int i=0;i<events.length-1;i++){
			events[i] = list[i].toString();
		}
		
		events[events.length-1] = "SportsEventsService.getSportEvents";
		impl.updateClientUI(events, State.GET_SPORT_EVENTS);
		return list; 
	}
	
	@ServiceOperation
	public SportEvent requestResult(SportEvent event) throws Exception {
		String[] tab = {"SportsEventsService.requestResult"};
		impl.updateClientUI(tab, State.LOCAL_OPERATION);
		return super.requestResult(event);
	}
}