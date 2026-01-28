package MsGrasa2026.ghosts.actions;

import MsGrasa2026.common.behavior.BehaviorContext;
import MsGrasa2026.pacman.Context;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;

public class ChasePacman extends GhostAction {
	public ChasePacman(GHOST ghost) {
		super(ghost);
	}
	@Override
	public MOVE apply(BehaviorContext context) {
		Context ctx = (Context)context;
		int PcPos = ctx.getPosition();
		int GPos = ctx.getGhostPosition(ghost);
		MOVE LastMove = ctx.getGhostLastMove(ghost);
		if(ctx.getGame().doesGhostRequireAction(ghost)) {
			return ctx.moveTowardsTarget(GPos, PcPos, LastMove, DM.EUCLID);
		}
		return LastMove;
	}
	@Override
	public String toString() {
		return "Chasing MsPacman -> "+ghost.name()+": "+hashCode();
	}
}
