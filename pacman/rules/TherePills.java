package MsGrasaTeam2026.pacman.rules;

import MsGrasaTeam2026.common.behavior.BehaviorContext;
import MsGrasaTeam2026.common.behavior.Rule;
import MsGrasaTeam2026.common.path.PathNode;
import MsGrasaTeam2026.common.path.PillEvaluator;
import MsGrasaTeam2026.pacman.Context;
import pacman.game.Constants.MOVE;

public class TherePills extends Rule {
	private MOVE[] possibleMoves;
	public MOVE[] getPossibleMoves() {
		return possibleMoves;
	}
	@Override
	public boolean evaluate(BehaviorContext context) {
		Context ctx = (Context)context;
	
		int PcPos = ctx.getPosition();
		MOVE lastMove = ctx.getLastMove();
		if(!ctx.isInJunction(PcPos)) return false;
		PillEvaluator pev = new PillEvaluator(ctx); 
		PathNode tree = ctx.getPathTree(PcPos, lastMove, 1, ctx.getColor());
		pev.compute(tree);
		int npaths = pev.getPathsWithPills().size();
		possibleMoves = new MOVE[npaths];
		if(npaths==0) return false;
		for (int i = 0; i < npaths; i++) {
			possibleMoves[i] = pev.getPathsWithPills().get(i).getFirstMove();
		}
		notify(context, "MsPacman has encountered pills");
		return true;
	}
	@Override
	public String toString() {
		return "There pills:"+hashCode();
	}
}
