package MsGrasaTeam2026.pacman.rules;

import MsGrasaTeam2026.common.behavior.BehaviorContext;
import MsGrasaTeam2026.common.behavior.ProxRule;
import MsGrasaTeam2026.common.locations.Pill;
import MsGrasaTeam2026.common.utils.NFunction;
import MsGrasaTeam2026.pacman.Context;
import pacman.game.Constants.DM;
import pacman.game.Constants.MOVE;

public class SoCloseOfPowerPill extends ProxRule {
	public SoCloseOfPowerPill(double threshold) {
		super(threshold);
	}
	private void fuzzify(double distance) {
		double r = 1.0d - distance / getThreshold();
		setWeight(NFunction.pPolyN(r, 2));
	}
	@Override
	public boolean evaluate(BehaviorContext context) {
		Context ctx = (Context)context;
		int PcPos = ctx.getPosition();
		MOVE LastMove = ctx.getLastMove();
		Pill nPPill = ctx.getNearestPowerPill(PcPos, LastMove, DM.PATH);
		if(nPPill==null) return false;
		double d = nPPill.getDistance(DM.PATH);
		fuzzify(d);
		if(d < getThreshold()) {
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
