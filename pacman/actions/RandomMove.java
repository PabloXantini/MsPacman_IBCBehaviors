package MsGrasaTeam2026.pacman.actions;

import java.util.Random;

import MsGrasaTeam2026.common.behavior.Action;
import MsGrasaTeam2026.common.behavior.BehaviorContext;
import MsGrasaTeam2026.pacman.Context;
import pacman.game.Constants.MOVE;

public class RandomMove extends Action{
	private Random random = new Random();
	public RandomMove() {}
	@Override
	public MOVE apply(BehaviorContext context) {
		Context ctx = (Context)context;
		MOVE[] moves = ctx.getPossibleMoves();
		return moves[random.nextInt(moves.length)];
	}
	@Override
	public String toString() {
		return "RandomMove:"+super.hashCode();
	}
}
