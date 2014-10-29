package com.soa.service.atomic.graphical;

import com.soa.object.Choice;
import com.soa.object.Odds;
import com.soa.object.SportEvent;
import com.soa.service.composite.BetCompositeService;
import com.webapp.client.event.State;
import com.webapp.server.WorkflowServiceImpl;

import service.auxiliary.LocalOperation;

public class GraphicalBetCompositeService extends BetCompositeService {
		
	private WorkflowServiceImpl impl;
	
	public GraphicalBetCompositeService(String path, WorkflowServiceImpl impl) {
		super(path);
		this.impl = impl;
	}
	
	@LocalOperation
	public double getAmount(SportEvent event) {
		String[] tab = {"this.getAmount"};
		impl.updateClientUI(tab, State.LOCAL_OPERATION);
		return super.getAmount(event);
	}
	
	@LocalOperation
	public double getTotalAmount() {
		String[] tab = {"this.getTotalAmount"};
		impl.updateClientUI(tab, State.LOCAL_OPERATION);
		return super.getTotalAmount();
	}
	
	@LocalOperation
	public void resetTotalAmount() {
		String[] tab = {"this.resetTotalAmount"};
		impl.updateClientUI(tab, State.LOCAL_OPERATION);
		super.resetTotalAmount();
	}
	
	@LocalOperation
	public Choice getChoice(SportEvent event, Odds odds) {
		String[] tab = {"this.getChoice"};
		impl.updateClientUI(tab, State.LOCAL_OPERATION);
		return super.getChoice(event, odds);
	}
}