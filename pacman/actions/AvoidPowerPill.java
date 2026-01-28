package MsGrasaTeam2026.pacman.actions;

import MsGrasaTeam2026.common.behavior.Action;
import MsGrasaTeam2026.common.behavior.BehaviorContext;
import MsGrasaTeam2026.common.locations.Pill;
import MsGrasaTeam2026.pacman.Context;
import pacman.game.Constants.DM;
import pacman.game.Constants.MOVE;

public class AvoidPowerPill extends Action {
	@Override
	public MOVE apply(BehaviorContext context) {
		Context ctx = (Context)context;
		int PcPos = ctx.getPosition();
		MOVE LastMove = ctx.getLastMove();
		Pill nPPill = ctx.getNearestPowerPill(PcPos, LastMove, DM.PATH);
		return ctx.moveAwayFromTarget(PcPos, nPPill.getPosition(), LastMove, DM.EUCLID);
	}
	@Override
	public String toString() {
		return "Avoiding PowerPill:"+hashCode();
	}
}
