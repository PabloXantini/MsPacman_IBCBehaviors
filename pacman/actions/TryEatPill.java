package MsGrasaTeam2026.pacman.actions;

import java.util.Random;

import MsGrasaTeam2026.common.behavior.Action;
import MsGrasaTeam2026.common.behavior.BehaviorContext;
import MsGrasaTeam2026.pacman.Context;
import MsGrasaTeam2026.pacman.rules.TherePills;
import pacman.game.Constants.MOVE;

public class TryEatPill extends Action{
	private Random random = new Random();
	private TherePills r1 = new TherePills();
	@Override
	public MOVE apply(BehaviorContext context) {
		Context ctx = (Context)context;
		MOVE[] moves; 
		if(r1.evaluate(ctx)) {
			moves = r1.getPossibleMoves();
			return moves[random.nextInt(moves.length)];
		}
		moves = ctx.getPossibleMoves();
		return moves[random.nextInt(moves.length)];
	}
	@Override
	public String toString() {
		return "Trying eating a Pill:"+hashCode();
	}
}
