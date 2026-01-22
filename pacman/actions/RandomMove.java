package MsGrasa2026.pacman.actions;

import java.util.Random;

import MsGrasa2026.common.Action;
import MsGrasa2026.common.BehaviorContext;
import MsGrasa2026.pacman.Context;
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

}
