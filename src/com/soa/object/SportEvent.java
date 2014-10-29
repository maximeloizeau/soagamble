package com.soa.object;

import java.util.Date;

public class SportEvent {

	private int id;
	private String hometeam;
	private String awayteam;
	private Choice result;
	private Date startDate;
	private Date endDate;
	private Choice favorite;
	
	public SportEvent(int matchId, String hometeam, String awayteam, Date startDate, Date endDate, Choice favorite) {
		this.id = matchId;
		this.hometeam = hometeam;
		this.awayteam = awayteam;
		this.startDate = startDate;
		this.endDate = endDate;
		this.favorite = favorite;
	}
	
	public Integer getId() {
		return id;
	}

	public Choice getFavorite(){
		return this.favorite;
	}
	
	public String getHometeam() {
		return hometeam;
	}

	public String getAwayteam() {
		return awayteam;
	}
	
	public void setResult(Choice result) {
		this.result = result;
	}
	
	public Choice getResult() {
		return result;
	}
	
	public Date getStartDate() {
		return this.startDate;
	}
	
	public Date getEndDate() {
		return this.endDate;
	}
	
	public String toString() {
		return "(" + id + ") " + hometeam + " : " + awayteam;
	}
}