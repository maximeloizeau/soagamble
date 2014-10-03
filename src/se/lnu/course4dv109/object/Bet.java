package se.lnu.course4dv109.object;

public class Bet {

	private double amount;
	private SportEvent match;
	
	public Bet(SportEvent match, double amount) {
		this.match = match;
		this.amount = amount;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public SportEvent getMatch() {
		return match;
	}
}
