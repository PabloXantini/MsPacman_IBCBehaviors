package MsGrasaTeam2026.ghosts.actions;

import java.util.Random;

import MsGrasaTeam2026.common.behavior.BehaviorContext;
import MsGrasaTeam2026.common.path.PathNode;
import MsGrasaTeam2026.pacman.Context;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;

public class BlockingChase extends GhostAction {
	private Random random = new Random();
	public BlockingChase(GHOST ghost) {
		super(ghost);
		// TODO Auto-generated constructor stub
	}
	@Override
	public MOVE apply(BehaviorContext context) {
		Context ctx = (Context) context;
		
		int pcPos = ctx.getPosition();
		int gPos  = ctx.getGhostPosition(ghost);
		MOVE lastMove = ctx.getLastMove();
		MOVE gLastMove = ctx.getGhostLastMove(ghost);

		PathNode pcTree = ctx.getPathTree(pcPos, lastMove, 1, ctx.getColor());
		
		PathNode[] paths = pcTree.getPaths();
		int[] targets = new int[paths.length];
		
		for (int i = 0; i < targets.length; i++) {
			targets[i] = paths[i].getOut();
		}

		if (ctx.getGame().doesGhostRequireAction(ghost)) {
			return ctx.moveTowardsTarget(gPos, targets[random.nextInt(paths.length)], gLastMove, DM.EUCLID);
		}
		return lastMove;
	}
}
