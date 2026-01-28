package MsGrasaTeam2026.ghosts.actions;

import java.util.Random;

import MsGrasaTeam2026.common.behavior.BehaviorContext;
import MsGrasaTeam2026.pacman.Context;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;

public class NervousChase extends GhostAction {
	private Random random = new Random();
	private double nerveDistance = 0.0d;
	public NervousChase(GHOST ghost, double nerveDistance) {
		super(ghost);
		this.nerveDistance = nerveDistance;
	}
	@Override
	public MOVE apply(BehaviorContext context) {
		Context ctx = (Context) context;

		int gPos  = ctx.getGhostPosition(ghost);
		int pcPos = ctx.getPosition();
		MOVE lastMove = ctx.getGhostLastMove(ghost);

		if (!ctx.getGame().doesGhostRequireAction(ghost))
			return lastMove;

		int distance = ctx.getGame().getShortestPathDistance(gPos, pcPos);

		if (distance < nerveDistance) {
			MOVE[] moves = ctx.getGame().getPossibleMoves(gPos, lastMove);
			return moves[random.nextInt(moves.length)];
		}

		return ctx.moveTowardsTarget(gPos, pcPos, lastMove, DM.PATH);
	}

}
