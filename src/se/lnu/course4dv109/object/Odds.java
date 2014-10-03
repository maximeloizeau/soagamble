package se.lnu.course4dv109.object;

public class Odds {

	private double[] values = new double[3];
	
	public Odds(double home, double draw, double away) {
		values[0] = home;
		values[1] = draw;
		values[2] = away;
	}
	
	public Double getOdds(Choice choice) {
		if (choice.equals(Choice.HOME_TEAM)) {
			return this.values[0];
			
		} else if (choice.equals(Choice.DRAW)) {
			return this.values[0];
				
		} else if (choice.equals(Choice.AWAY_TEAM)) {
			return this.values[0];
		}

		return null;
	}
}
