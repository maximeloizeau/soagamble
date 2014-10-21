package se.lnu.course4dv109.object;

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
}
