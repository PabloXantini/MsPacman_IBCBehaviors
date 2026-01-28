package MsGrasa2026.pacman.rules;

import MsGrasa2026.common.behavior.BehaviorContext;
import MsGrasa2026.common.behavior.ProxRule;
import MsGrasa2026.common.locations.Ghost;
import MsGrasa2026.common.utils.NFunction;
import MsGrasa2026.pacman.Context;
import pacman.game.Constants.DM;

public class AwayFromGhosts extends ProxRule {
	public AwayFromGhosts(double threshold) {
		super(threshold);
	}
	private void fuzzify(double distance) {
		double r = distance / getThreshold();
		setWeight(NFunction.pPolyN(r, 2));
	}
	@Override
	public boolean evaluate(BehaviorContext context) {
		Context ctx = (Context)context;
		int PcPos = ctx.getPosition();
		Ghost nGhost = ctx.getNearestGhost(PcPos, ctx.getFreeGhosts(), DM.PATH);
		if(nGhost==null) return false;
		double d = nGhost.getDistance(DM.PATH);
		fuzzify(d);
		if(d > getThreshold()) return true;
		return false;
	}
	@Override
	public String toString() {
		return "Away from Ghosts: "+hashCode();
	}
}
