package MsGrasa2026.pacman.rules;

import MsGrasa2026.common.behavior.BehaviorContext;
import MsGrasa2026.common.behavior.ProxRule;
import MsGrasa2026.common.locations.Ghost;
import MsGrasa2026.common.utils.NFunction;
import MsGrasa2026.pacman.Context;
import pacman.game.Constants.DM;
import pacman.game.Constants.MOVE;

public class NearToGhosts extends ProxRule{
	public NearToGhosts(double threshold) {
		super(threshold);
	}
	private void fuzzify(double distance) {
		double r = 1.0d - distance / getThreshold();
		//System.out.println(r);
		setWeight(NFunction.pPolyN(r, 2));
	}
	@Override
	public boolean evaluate(BehaviorContext context) {
		Context ctx = (Context)context;
		int PcPos = ctx.getPosition();
		//MOVE LastMove = ctx.getLastMove();
		Ghost nGhost = ctx.getNearestGhost(PcPos, ctx.getFreeGhosts(), DM.PATH);
		if(nGhost==null) return false;
		double d = nGhost.getDistance(DM.PATH);
		fuzzify(d);
		if(d < getThreshold()) return true;
		return false;
	}
	@Override
	public String toString() {
		return "Near to Ghosts: "+hashCode();
	}
}
