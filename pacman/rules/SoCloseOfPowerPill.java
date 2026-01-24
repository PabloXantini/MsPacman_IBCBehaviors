package MsGrasa2026.pacman.rules;

import MsGrasa2026.common.behavior.BehaviorContext;
import MsGrasa2026.common.behavior.Rule;
import MsGrasa2026.common.locations.Pill;
import MsGrasa2026.pacman.Context;
import pacman.game.Constants.DM;
import pacman.game.Constants.MOVE;

public class SoCloseOfPowerPill extends Rule {
	private double threshold = 0;
	public SoCloseOfPowerPill(double threshold) {
		this.threshold = threshold;
	}
	public void setThreshold(double newValue) {
		this.threshold = newValue;
	}
	@Override
	public boolean evaluate(BehaviorContext context) {
		Context ctx = (Context)context;
		int PcPos = ctx.getPosition();
		MOVE LastMove = ctx.getLastMove();
		Pill nPPill = ctx.getNearestPowerPill(PcPos, LastMove, DM.PATH);
		if(nPPill!=null && nPPill.getDistance(DM.PATH) < threshold) {
			int[] path = ctx.getShortestPath(PcPos, nPPill.getPosition(), LastMove);
			int[] wpath = ctx.computePathFreedomDegrees(path);
			if(!ctx.thereAlternatives(wpath)) return true;
		}
		return false;
	}
	@Override
	public String toString() {
		return "CloseOfPowerPill:"+hashCode();
	}
}
