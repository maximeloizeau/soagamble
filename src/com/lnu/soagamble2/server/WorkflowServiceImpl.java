package com.lnu.soagamble2.server;


import se.lnu.course4dv109.client.Client;
import se.lnu.course4dv109.service.atomic.BankService;
import se.lnu.course4dv109.service.atomic.BetService;
import se.lnu.course4dv109.service.atomic.OddsService;
import se.lnu.course4dv109.service.atomic.SportsEventsService;
import se.lnu.course4dv109.service.composite.BetCompositeService;
import service.registry.ServiceRegistry;

import com.lnu.soagamble2.client.ServerMessageGeneratorService;
import com.lnu.soagamble2.client.WorkflowService;
import com.lnu.soagamble2.client.event.UpdateUIEvent;

import de.novanic.eventservice.client.event.Event;
import de.novanic.eventservice.service.RemoteEventServiceServlet;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class WorkflowServiceImpl extends RemoteEventServiceServlet implements
WorkflowService, ServerMessageGeneratorService {


	@Override
	public String initialize() {
		String[] args = {};

		ServiceRegistry.main(args);
		BetService.main(args);
		BankService.main(args);
		OddsService.main(args);
		SportsEventsService.main(args, this);
		String[] path = {getServletContext().getRealPath("gamble-workflow.txt")};
		BetCompositeService.main(path);


		return "OK";
	}
	
	public String createClient() {
		String[] args = {};
		Client.main(args);
		
		return "Client created";
	}

	public void updateClientUI(String[] object, int state) {
		Event theEvent = new UpdateUIEvent(object, state);
        //add the event, so clients can receive it
        addEvent(UpdateUIEvent.SERVER_MESSAGE_DOMAIN, theEvent);
	}

	@Override
	public synchronized void start() {
		System.out.println("Workflow start ???");
	}
}
