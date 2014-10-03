package se.lnu.course4dv109.object;

public class Bet {

	private double money;
	private Choice choice;
	
	public Bet(Choice choice, double money) {
		this.choice = choice;
		this.money = money;
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
}
