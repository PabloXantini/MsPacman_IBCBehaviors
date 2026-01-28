package MsGrasaTeam2026.ghosts.actions;

import MsGrasaTeam2026.common.behavior.BehaviorContext;
import MsGrasaTeam2026.pacman.Context;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;

public class RunAway extends GhostAction {
	public RunAway(GHOST ghost) {
		super(ghost);
	}
	@Override
	public MOVE apply(BehaviorContext context) {
		Context ctx = (Context) context;

		int pcPos = ctx.getPosition();
		int gPos  = ctx.getGhostPosition(ghost);
		MOVE last = ctx.getGhostLastMove(ghost);

		if (ctx.getGame().doesGhostRequireAction(ghost)) {
			return ctx.moveAwayFromTarget(gPos, pcPos, last, DM.EUCLID);
		}
		return last;
	}

	@Override
	public String toString() {
		return "Running Away from MsPacman -> " + ghost.name();
	}
}
