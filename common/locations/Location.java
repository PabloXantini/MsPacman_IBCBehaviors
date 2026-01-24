package MsGrasa2026.common.locations;

import pacman.game.Constants.DM;

public class Location {
	private int position;
	private double euclidianDistance;
	private double manhattanDistance;
	private double pathDistance;
	public Location(int position) {
		this.position = position;
	}
	public int getPosition() {
		return position;
	}
	public double getDistance(DM metric) {
		switch (metric) {
		case EUCLID: return euclidianDistance;
		case MANHATTAN: return manhattanDistance;
		case PATH: return pathDistance;
		default: return 0.0d;
		}
	}
	public void setDistance(double distance, DM metric) {
		switch (metric) {
		case EUCLID:
			this.euclidianDistance = distance;
			break;
		case MANHATTAN:
			this.manhattanDistance = distance;
			break;
		case PATH:
			this.pathDistance = distance;
			break;
		default:
			break;
		}
	}
}
