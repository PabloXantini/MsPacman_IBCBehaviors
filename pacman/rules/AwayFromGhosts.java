package MsGrasa2026.pacman.rules;

import MsGrasa2026.common.behavior.BehaviorContext;
import MsGrasa2026.common.behavior.ProxRule;
import MsGrasa2026.common.locations.Ghost;
import MsGrasa2026.pacman.Context;
import pacman.game.Constants.DM;

public class AwayFromGhosts extends ProxRule {
	public AwayFromGhosts(double threshold) {
		super(threshold);
	}
	@Override
	public boolean evaluate(BehaviorContext context) {
		Context ctx = (Context)context;
		int PcPos = ctx.getPosition();
		Ghost nGhost = ctx.getNearestGhost(PcPos, ctx.getFreeGhosts(), DM.PATH);
		if(nGhost==null) return false;
		double d = nGhost.getDistance(DM.PATH);
		if(d > getThreshod()*0.2d) setWeight(0.1);
		else if(d > getThreshod()*0.4d) setWeight(0.15);
		else if(d > getThreshod()*0.6d) setWeight(0.3);
		else if(d > getThreshod()*0.8d) setWeight(0.6);
		else if(d > getThreshod()) setWeight(1.0);
		if(d > getThreshod()) return true;
		return false;
	}
	@Override
	public String toString() {
		return "Near to Ghosts: "+hashCode();
	}
}
