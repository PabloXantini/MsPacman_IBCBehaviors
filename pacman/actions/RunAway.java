package MsGrasaTeam2026.pacman.actions;

import java.awt.Color;
import java.util.EnumMap;
import java.util.Map.Entry;

import MsGrasaTeam2026.common.behavior.Action;
import MsGrasaTeam2026.common.behavior.BehaviorContext;
import MsGrasaTeam2026.common.locations.Ghost;
import MsGrasaTeam2026.common.path.OutComparator;
import MsGrasaTeam2026.common.path.PathNode;
import MsGrasaTeam2026.pacman.Context;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;

public class RunAway extends Action{
	private final double threshold;
	public RunAway(double threshold) {
		this.threshold = threshold;
	}
	private void printMoveTable(EnumMap<MOVE, Double> table) {
		System.out.println("Move table done in this instant");
		for(Entry<MOVE, Double> e : table.entrySet()) {
			System.out.println("MOVE: "+e.getKey()+" W: "+e.getValue());
		}
	}
	@Override
	public MOVE apply(BehaviorContext context) {
		Context ctx = (Context)context;
		OutComparator oc = new OutComparator(ctx);
		oc.reset();
		//MsPacman
		int PcPos = ctx.getPosition();
		MOVE LastMove = ctx.getLastMove();
		PathNode pTree = ctx.getPathTree(PcPos, LastMove, 1, ctx.getColor());
		//Ghosts
		GHOST[] ghosts = ctx.getMenacingGhosts();
		if(ghosts.length == 0) return LastMove;
		Ghost nGhost = ctx.getNearestGhost(PcPos, ghosts, DM.PATH);
		for(Ghost g : ctx.getNearGhostsByDistanceAway(PcPos, ghosts, DM.PATH, threshold)) {
			GHOST n = g.getName();
			PathNode gTree = ctx.getPathTree(g.getPosition(), g.getLastMove(), 1, ctx.getGhostColor(n));
			gTree.accept(oc);
		}
		MOVE bestMove = ctx.moveAwayFromTarget(PcPos, nGhost.getPosition(), LastMove, DM.EUCLID);;
		EnumMap<MOVE, Double> runAwayScores = new EnumMap<MOVE, Double>(MOVE.class);
		for(PathNode p : pTree.getPaths()) {
			MOVE move = p.getFirstMove();
			double pscore = p.getSteps();
			double worst = Double.POSITIVE_INFINITY;
			//runAwayScores.put(move, Double.POSITIVE_INFINITY);
			for(PathNode gp : oc.getPaths()) {
				if(p.getOut() == gp.getOut()) {					
					if(ctx.getDebug()) ctx.addPoints(Color.WHITE, gp.getOut());
					double score = gp.getSteps();
					double danger = pscore - score;
					worst = Math.min(worst, danger);
					//runAwayScores.merge(move, danger, Double::min);
				}
			}
			runAwayScores.put(move, worst);
		}
		//There maybe here 2 scopes
		//MAXIMIZE STRATEGY -> Max score -> less risk
		//MINIMIZE STRATEGY -> Min score -> more safety
		double max = Double.NEGATIVE_INFINITY;
		for(Entry<MOVE, Double> e : runAwayScores.entrySet()) {
			double v = e.getValue();
			MOVE move = e.getKey();
			if(max < v) {
				max = v;
				bestMove = move;
			}
		}
		if(ctx.getDebug()) printMoveTable(runAwayScores);
		return bestMove;
	}
	@Override
	public String toString() {
		return "Running away:"+hashCode();
	}
}
