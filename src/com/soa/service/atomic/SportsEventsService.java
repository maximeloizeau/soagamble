package com.soa.service.atomic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.soa.object.Choice;
import com.soa.object.SportEvent;
import com.soa.service.atomic.graphical.GraphicalSportsEventsService;
import com.webapp.client.event.State;
import com.webapp.server.WorkflowServiceImpl;

import service.atomic.AtomicService;
import service.auxiliary.ServiceOperation;

public class SportsEventsService extends AtomicService {

	private List<SportEvent> events = new ArrayList<SportEvent>();
	protected WorkflowServiceImpl impl;

	public SportsEventsService(String serviceName, String serviceEndpoint) {
		super(serviceName, serviceEndpoint);
	}

	// @ServiceOperation
	public SportEvent requestResult(int matchId) throws Exception {
		System.out.println("[4DV109] SportsEventsService.requestResult");

		SportEvent event = this.getSportEvent(matchId);

		if (event == null) {
			throw new Exception("SportEvent with matchId " + matchId + " not found!");
		}

		if (event.getResult() == null) {
			Choice result = this.getRandomResult(event);
			event.setResult(result);
		}

		return event;
	}

	@ServiceOperation
	public SportEvent requestResult(SportEvent event) throws Exception {
		return this.requestResult(event.getId());
	}

	@ServiceOperation
	public SportEvent[] getSportEvents() {
		System.out.println("[4DV109] SportsEventsService.getSportEvents");

		this.events.clear();
		this.createRandomMatches();

		SportEvent[] list = this.events.toArray(new SportEvent[this.events.size()]);
		return list;
	}

	@ServiceOperation
	public SportEvent getSportEvent(int matchId) throws Exception {
		if (events.size() == 0) {
			this.createRandomMatches();
		}

		for (SportEvent event : this.events) {
			if (event.getId() == matchId) {
				return event;
			}
		}

		throw new Exception("SportEvent with matchId " + matchId + " not found!");
	}

	private void createRandomMatches() {
		int matchId = 1;

		Calendar start = Calendar.getInstance();
		start.set(Calendar.HOUR_OF_DAY, 20);
		start.set(Calendar.MINUTE, 45);

		Calendar end = Calendar.getInstance();
		start.set(Calendar.HOUR_OF_DAY, 22);
		start.set(Calendar.MINUTE, 30);

		ArrayList<String> teams = new ArrayList<String>();
		teams.add("Manchester City");
		teams.add("FC Barcelona");
		teams.add("Bayer 04 Leverkusen");
		teams.add("Paris Saint-Germain");
		teams.add("AC Mailand");
		teams.add("Atl�tico Madrid");
		teams.add("FC Arsenal");
		teams.add("FC Bayern M�nchen");
		teams.add("Zenit Sankt Petersburg");
		teams.add("Borussia Dortmund");
		teams.add("Olympiakos Pir�us");
		teams.add("Manchester United");
		teams.add("FC Schalke 04");
		teams.add("Real Madrid");
		teams.add("Galatasaray Istanbul");
		teams.add("FC Chelsea");

		while (teams.size() > 1) {			
			String home = this.getRandomTeam(teams);
			String away = this.getRandomTeam(teams);

			events.add(new SportEvent(matchId, home, away, start.getTime(), end.getTime(), getRandomFavorite()));
			matchId++;
		}
	}

	private String getRandomTeam(ArrayList<String> teams) {
		int idx = this.getRandom(teams.size());
		String name = teams.get(idx);
		teams.remove(idx);

		return name;
	}

	private int getRandom(int max) {
		return (new Random()).nextInt(max);
	}

	private Choice getRandomResult(SportEvent event) {
		//int rand = this.getRandom(100000) % 3;
		int rand = this.getRandom(74)+ 1;
		if(rand<=75){
			return event.getFavorite();
		}
		else if(rand>75 && rand<88){
			return this.getOtherResults(event.getFavorite())[0];
		}
		else{
			return this.getOtherResults(event.getFavorite())[1];
		}
	}

	private Choice getRandomFavorite(){
		int rand = this.getRandom(100000) % 3;

		switch (rand) {
		case 0:
			return Choice.HOME_TEAM;

		case 1:
			return Choice.DRAW;

		case 2:
			return Choice.AWAY_TEAM;
		}

		return null;
	}

	private Choice[] getOtherResults(Choice choice){
		Choice[] c = new Choice[2];
		switch(choice){
		case HOME_TEAM:
			c[0] = Choice.DRAW;
			c[1] = Choice.AWAY_TEAM;
			return c ;
		case AWAY_TEAM:
			c[0] = Choice.HOME_TEAM;
			c[1] = Choice.DRAW;
			return c ;
		case DRAW:
			c[0] = Choice.AWAY_TEAM;
			c[1] = Choice.HOME_TEAM;
			return c ;
		default:
			return null;
		}
	}

	public static SportsEventsService main(String[] args, WorkflowServiceImpl impl) {
		SportsEventsService sportsEventsService = new GraphicalSportsEventsService("SportsEventsService", "se.lnu.course4dv109.service.sportsevents", impl);

		Map<String, Object> customProperties = sportsEventsService.getServiceDescription().getCustomProperties();
		customProperties.put("Performance", 2);
		customProperties.put("DataReliability", true);

		sportsEventsService.startService();
		sportsEventsService.register();

		return sportsEventsService;
	}
}