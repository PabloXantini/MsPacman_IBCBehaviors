package MsGrasaTeam2026.common.path;

import java.util.ArrayList;

import MsGrasaTeam2026.common.behavior.BehaviorContext;

public class OutComparator extends ComparatorPathVisitor {
	private final ArrayList<PathNode> paths = new ArrayList<PathNode>();
	public OutComparator(BehaviorContext context) {
		super(context);
	}
	public ArrayList<PathNode> getPaths(){
		return paths;
	}
	public void reset() {
		paths.clear();
	}
	@Override
	public void visit(PathNode node) {
		double score = node.getScore();
		score+=node.getSteps();
		node.setScore(score);
		paths.add(node);
		if(node.getPaths()==null) return;
		for (PathNode path : node.getPaths()) {
			path.setScore(node.getScore());
			path.accept(this);
		}
	}
	@Override
	public void compare(PathNode node1, PathNode node2) {
	}
}
