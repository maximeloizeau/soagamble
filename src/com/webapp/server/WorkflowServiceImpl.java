package com.webapp.server;


import service.provider.AbstractService;
import service.registry.ServiceRegistry;

import com.soa.client.Client;
import com.soa.service.atomic.BankService;
import com.soa.service.atomic.BetService;
import com.soa.service.atomic.OddsService;
import com.soa.service.atomic.OddsWithFavoriteService;
import com.soa.service.atomic.OddsWithoutFavoriteService;
import com.soa.service.atomic.SportsEventsService;
import com.soa.service.atomic.graphical.GraphicalSportsEventsService;
import com.soa.service.composite.BetCompositeService;
import com.soa.service.registry.BetServiceRegistry;
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
	private AbstractService[] services = new AbstractService[7];
	
	private Client client;
	
	public String result;
	public int waitingTime = 1000;

	@Override
	public String initialize() {
		String[] args = {};

		if(services[0] == null) {
			services[0] = BetServiceRegistry.start(); 
		}
		if(services[1] == null) {
			services[1] = BetService.main(args, this);
		}
		if(services[2] == null) {
			services[2] = BankService.main(args, this);
		}
		if(services[3] == null) {
			services[3] = OddsWithFavoriteService.main(args, this); 
		}
		if(services[4] == null) {
			services[4] = SportsEventsService.main(args, this);
		}
		if(services[5] == null) {
			String[] path = {getServletContext().getRealPath("gamble-workflow.txt")};
			services[5] = BetCompositeService.main(path, this); 
		}
		if(services[6] == null){
			services[6] = OddsWithoutFavoriteService.main(args, this);
		}
	
		return "OK";
	}
	
	public Double createClient(int waitingTime, boolean favorite) {
		this.waitingTime = waitingTime;
		
		if(client == null) {
			client = new Client();
		}
		Double result = client.start(favorite);
		
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
