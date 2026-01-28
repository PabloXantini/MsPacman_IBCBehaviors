package MsGrasaTeam2026.pacman.actions;

import MsGrasaTeam2026.common.behavior.Action;
import MsGrasaTeam2026.common.behavior.BehaviorContext;
import MsGrasaTeam2026.common.locations.Ghost;
import MsGrasaTeam2026.pacman.Context;
import pacman.game.Constants.DM;
import pacman.game.Constants.MOVE;

public class ChaseGhost extends Action{

	@Override
	public MOVE apply(BehaviorContext context) {
		Context ctx = (Context)context;
		int PcPos = ctx.getPosition();
		MOVE LastMove = ctx.getLastMove();
		Ghost nGhost = ctx.getNearestGhostToPursue(PcPos, LastMove, ctx.getVulnerableGhosts(), DM.PATH);
		MOVE chaseMove = ctx.moveTowardsTarget(PcPos, nGhost.getPosition(), LastMove, DM.EUCLID);
		return chaseMove;
	}
	@Override
	public String toString() {
		return "Chasing ghosts:"+hashCode();
	}
}
