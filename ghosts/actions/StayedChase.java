package MsGrasaTeam2026.ghosts.actions;

import MsGrasaTeam2026.common.behavior.BehaviorContext;
import MsGrasaTeam2026.common.locations.Pill;
import MsGrasaTeam2026.pacman.Context;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;

public class StayedChase extends GhostAction {
	public StayedChase(GHOST ghost) {
		super(ghost);
	}
	@Override
	public MOVE apply(BehaviorContext context) {
		Context ctx = (Context) context;

		int gPos  = ctx.getGhostPosition(ghost);
		int pcPos = ctx.getPosition();
		MOVE pcLastMove = ctx.getLastMove();
		MOVE lastMove = ctx.getGhostLastMove(ghost);

		Pill PPill = ctx.getNearestPowerPill(pcPos, pcLastMove, DM.PATH);
		
		int target;
		
		if (PPill == null) target = pcPos; else target = PPill.getPosition();
		
		if (ctx.getGame().doesGhostRequireAction(ghost)) {
			return ctx.moveTowardsTarget(gPos, target, lastMove, DM.PATH);
		}
		return lastMove;
	}
}
