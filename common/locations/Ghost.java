package MsGrasaTeam2026.common.locations;

import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;

public class Ghost extends Location {
	private double risk;
	private GHOST name;
	private MOVE last;
	public Ghost(GHOST name, int position) {
		super(position);
		this.name = name;
	}
	public GHOST getName() {
		return name;
	}
	public MOVE getLastMove() {
		return last;
	}
	public double getRisk() {
		return risk;
	}
	public void setLastMove(MOVE move) {
		this.last = move;
	}
	public void setRisk(double risk) {
		this.risk = risk;
	}
}
