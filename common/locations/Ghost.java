package MsGrasa2026.common.locations;

import pacman.game.Constants.GHOST;

public class Ghost extends Location {
	private double risk;
	private GHOST name;
	public Ghost(GHOST name, int position) {
		super(position);
		this.name = name;
	}
	public GHOST getName() {
		return name;
	}
	public double getRisk() {
		return risk;
	}
	public void setRisk(double risk) {
		this.risk = risk;
	}
}
