package com.soa.object;

public class Bet {

	private double money;
	private Choice choice;
	private int matchId;
	
	public Bet(Choice choice, double money, int matchId) {
		this.choice = choice;
		this.money = money;
		this.matchId = matchId;
	}

	public double getAmount() {
		return money;
	}

	public void setAmount(double amount) {
		this.money = amount;
	}

	public Choice getChoice() {
		return choice;
	}
	
	public int getMatchId() {
		return this.matchId;
	}
	
	public String toString() {
		return "Choice: " + getChoiceName() + " - Amount: " + this.money;
	}
	
	public String getChoiceName() {
		if (this.choice == Choice.HOME_TEAM) {
			return "Home";
				
		} else if (this.choice == Choice.DRAW) {
			return "Draw";
				
		} else if (this.choice == Choice.AWAY_TEAM) {
			return "Away";		
		}
		
		return "";
	}
}
