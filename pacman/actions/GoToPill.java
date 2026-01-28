package MsGrasa2026.pacman.actions;

import MsGrasa2026.common.behavior.Action;
import MsGrasa2026.common.behavior.BehaviorContext;
import MsGrasa2026.common.locations.Pill;
import MsGrasa2026.pacman.Context;
import pacman.game.Constants.DM;
import pacman.game.Constants.MOVE;

public class GoToPill extends Action{

	@Override
	public MOVE apply(BehaviorContext context) {
		Context ctx = (Context)context;
		int PcPos = ctx.getPosition();
		MOVE LastMove = ctx.getLastMove();
		Pill nPPill = ctx.getNearestPowerPill(PcPos, LastMove, DM.PATH);
		MOVE bestMove = LastMove;
		if(nPPill!=null) {
			bestMove = ctx.moveTowardsTarget(PcPos, nPPill.getPosition(), LastMove, DM.PATH);
		}
		return bestMove;
	}
	@Override
	public String toString() {
		return "MsPacman going to Power Pill";
	}
}
