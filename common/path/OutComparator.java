package MsGrasa2026.common.path;

import java.util.HashSet;
import java.util.Set;

import MsGrasa2026.common.behavior.BehaviorContext;
import pacman.game.Game;

public class OutComparator extends GeneralPathVisitor {
	private final Set<Integer> outs = new HashSet<Integer>();
	public OutComparator(BehaviorContext context) {
		super(context);
	}
	public Set<Integer> getOuts(){
		return outs;
	}
	public void compute(PathNode node) {
		outs.clear();
		node.accept(this);
	}
	@Override
	public void visit(PathNode node) {
		outs.add(node.getOut());
		if(node.getPaths()==null) return;
		for (PathNode path : node.getPaths()) {
			path.accept(this);
		}
	}
}
