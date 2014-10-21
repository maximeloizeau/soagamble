package se.lnu.course4dv109.object;

import java.util.Date;

public class SportEvent {

	private int Id;
	private String hometeam;
	private String awayteam;
	private Choice result;
	private Date startDate;
	private Date endDate;
	
	public SportEvent(int matchId, String hometeam, String awayteam, Date startDate, Date endDate) {
		this.Id = matchId;
		this.hometeam = hometeam;
		this.awayteam = awayteam;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	public int getId() {
		return Id;
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
}