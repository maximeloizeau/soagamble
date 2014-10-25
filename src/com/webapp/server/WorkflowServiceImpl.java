package com.webapp.server;


import service.registry.ServiceRegistry;

import com.soa.client.Client;
import com.soa.service.atomic.BankService;
import com.soa.service.atomic.BetService;
import com.soa.service.atomic.OddsService;
import com.soa.service.atomic.SportsEventsService;
import com.soa.service.atomic.graphical.GraphicalSportsEventsService;
import com.soa.service.composite.BetCompositeService;
import com.webapp.client.ServerMessageGeneratorService;
import com.webapp.client.WorkflowService;
import com.webapp.client.event.UpdateUIEvent;

import de.novanic.eventservice.client.event.Event;
import de.novanic.eventservice.service.RemoteEventServiceServlet;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class WorkflowServiceImpl extends RemoteEventServiceServlet implements
WorkflowService, ServerMessageGeneratorService {
	
	public String result;
	public int waitingTime = 1000;

	@Override
	public String initialize() {
		String[] args = {};

		ServiceRegistry.main(args);
		BetService.main(args, this);
		BankService.main(args, this);
		OddsService.main(args, this);
		SportsEventsService.main(args, this);
		String[] path = {getServletContext().getRealPath("gamble-workflow.txt")};
		BetCompositeService.main(path, this);


		return "OK";
	}
	
	public Double createClient(int waitingTime) {
		this.waitingTime = waitingTime;
		
		String[] args = {};
		Double result = Client.main(args);
		
		return Math.floor(result * 100) / 100;
	}

	public void updateClientUI(String[] object, int state) {
		Event theEvent = new UpdateUIEvent(object, state);
        //add the event, so clients can receive it
        addEvent(UpdateUIEvent.SERVER_MESSAGE_DOMAIN, theEvent);
        
        try {
			Thread.sleep(this.waitingTime);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public synchronized void start() {
		System.out.println("Workflow start ???");
	}
}
