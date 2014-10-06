package se.lnu.course4dv109.service.atomic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Random;

import se.lnu.course4dv109.object.Choice;
import se.lnu.course4dv109.object.SportEvent;
import service.atomic.AtomicService;
import service.auxiliary.ServiceOperation;

public class SportsEventsService extends AtomicService {

	private List<SportEvent> events = new ArrayList<SportEvent>();
	
	public SportsEventsService(String serviceName, String serviceEndpoint) {
		super(serviceName, serviceEndpoint);
	}

	@ServiceOperation
	public SportEvent requestResult(int matchId) throws Exception {
		System.out.println("[4DV109] SportsEventsService.requestResult");
		
		SportEvent event = this.getSportEvent(matchId);
		
		if (event == null) {
			throw new Exception("SportEvent with matchId " + matchId + " not found!");
		}
		
		if (event.getResult() == null) {
			Choice result = this.getRandomResult();
			event.setResult(result);
		}
		
		return event;
	}
	
	@ServiceOperation
	public List<SportEvent> requestSportEvents() {
		System.out.println("[4DV109] SportsEventsService.requestSportEvents");
		
		if (events.size() == 0) {
			this.createRandomMatches();
		}
		
		return this.events;
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
		teams.add("Atlético Madrid");
		teams.add("FC Arsenal");
		teams.add("FC Bayern München");
		teams.add("Zenit Sankt Petersburg");
		teams.add("Borussia Dortmund");
		teams.add("Olympiakos Piräus");
		teams.add("Manchester United");
		teams.add("FC Schalke 04");
		teams.add("Real Madrid");
		teams.add("Galatasaray Istanbul");
		teams.add("FC Chelsea");
		
		while (teams.size() > 1) {			
			String home = this.getRandomTeam(teams);
			String away = this.getRandomTeam(teams);
			
			System.out.println("[4DV109] Match (" + matchId + ") - " + home + " : " + away);
			
			events.add(new SportEvent(matchId, home, away, start.getTime(), end.getTime()));
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
	
	private Choice getRandomResult() {
		int rand = this.getRandom(2);
		
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
	
	
	public static void main(String[] args) {
		SportsEventsService sportsEventsService = new SportsEventsService("SportsEventsService", "se.lnu.course4dv109.service.sportsevents");
		
		Map<String, Object> customProperties = sportsEventsService.getServiceDescription().getCustomProperties();
		customProperties.put("Performance", 2);
		customProperties.put("DataReliability", true);

		sportsEventsService.startService();
		sportsEventsService.register();
	}
}