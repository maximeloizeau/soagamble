package com.lnu.course4dv109.service.actomic.graphical;

import com.lnu.soagamble2.client.event.State;
import com.lnu.soagamble2.server.WorkflowServiceImpl;

import se.lnu.course4dv109.object.SportEvent;
import se.lnu.course4dv109.service.atomic.SportsEventsService;
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