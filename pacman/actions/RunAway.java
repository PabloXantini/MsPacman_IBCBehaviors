package MsGrasa2026.pacman.actions;

import MsGrasa2026.common.behavior.Action;
import MsGrasa2026.common.behavior.BehaviorContext;
import MsGrasa2026.common.path.PathNode;
import MsGrasa2026.pacman.Context;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;

public class RunAway extends Action{
	@Override
	public MOVE apply(BehaviorContext context) {
		Context ctx = (Context)context;
		int PcPos = ctx.getPosition();
		MOVE LastMove = ctx.getLastMove();
		for(GHOST g : ctx.getFreeGhosts()) {
			PathNode gtree = ctx.getPathTree(PcPos, LastMove, 2, ctx.getGhostColor(g));
		}
		return null;
	}

}
