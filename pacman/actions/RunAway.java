package MsGrasa2026.pacman.actions;

import java.awt.Color;
import java.util.EnumMap;
import java.util.Map.Entry;

import MsGrasa2026.common.behavior.Action;
import MsGrasa2026.common.behavior.BehaviorContext;
import MsGrasa2026.common.locations.Ghost;
import MsGrasa2026.common.path.OutComparator;
import MsGrasa2026.common.path.PathNode;
import MsGrasa2026.pacman.Context;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;

public class RunAway extends Action{
	private final double threshold;
	public RunAway(double threshold) {
		this.threshold = threshold;
	}
	@Override
	public MOVE apply(BehaviorContext context) {
		Context ctx = (Context)context;
		OutComparator oc = new OutComparator(ctx);
		oc.reset();
		int PcPos = ctx.getPosition();
		MOVE LastMove = ctx.getLastMove();
		PathNode ptree = ctx.getPathTree(PcPos, LastMove, 1, ctx.getColor());
		GHOST[] ghosts = ctx.getMenacingGhosts();
		if(ghosts.length == 0) return LastMove;
		Ghost nGhost = ctx.getNearestGhost(PcPos, ghosts, DM.PATH);
		for(Ghost g : ctx.getNearGhostsByDistanceAway(PcPos, ghosts, DM.PATH, threshold)) {
			GHOST n = g.getName();
			PathNode gtree = ctx.getPathTree(g.getPosition(), g.getLastMove(), 3, ctx.getGhostColor(n));
			gtree.accept(oc);
		}
		EnumMap<MOVE, Double> runAwayScores = new EnumMap<MOVE, Double>(MOVE.class);
		for(PathNode obs : ptree.getPaths()) {
			for(PathNode gobs : oc.getPaths()) {
				int gout = gobs.getOut();
				if(obs.getOut() == gobs.getOut()) {
					if(ctx.getDebug()) ctx.addPoints(Color.WHITE, obs.getOut());
					MOVE runAwayMove;
					//runAwayMove = ctx.moveAwayFromTarget(PcPos, gout, LastMove, DM.PATH);
					runAwayMove = obs.getFirstMove();
					double score = gobs.getScore();
					runAwayScores.merge(runAwayMove, score, Double::min);
				}
			}
		}
		MOVE bestMove = ctx.moveAwayFromTarget(PcPos, nGhost.getPosition(), LastMove, DM.PATH);
		double max = Double.NEGATIVE_INFINITY;
		for(Entry<MOVE, Double> e : runAwayScores.entrySet()) {
			double v = e.getValue();
			if(max < v) {
				max = v;
				bestMove = e.getKey();
			}
		}
		return bestMove;
	}
	@Override
	public String toString() {
		return "Running away:"+hashCode();
	}
}
