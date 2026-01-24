package MsGrasa2026.pacman.rules;

import MsGrasa2026.common.behavior.BehaviorContext;
import MsGrasa2026.common.behavior.Rule;
import MsGrasa2026.common.locations.Ghost;
import MsGrasa2026.pacman.Context;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;

public class NearToGhosts extends Rule{
	private double threshold = 0.0d;
	public NearToGhosts(double threshold) {
		this.threshold = threshold;
	}
	@Override
	public boolean evaluate(BehaviorContext context) {
		Context ctx = (Context)context;
		int PcPos = ctx.getPosition();
		MOVE LastMove = ctx.getLastMove();
		Ghost nGhost = ctx.getNearestGhost(PcPos, LastMove, ctx.getFreeGhosts(), DM.PATH);
		if(nGhost==null) return false;
		if(nGhost.getDistance(DM.PATH) < threshold) return true;
		return false;
	}
}
