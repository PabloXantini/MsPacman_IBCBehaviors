package MsGrasaTeam2026.common.path;

import java.util.ArrayList;

import MsGrasaTeam2026.common.behavior.BehaviorContext;

public class PillEvaluator extends GeneralPathVisitor {
	private final ArrayList<PathNode> pillPaths = new ArrayList<PathNode>();
	public PillEvaluator(BehaviorContext context) {
		super(context);
	}
	public ArrayList<PathNode> getPathsWithPills(){
		return pillPaths;
	}
	public void compute(PathNode node) {
		pillPaths.clear();
		node.accept(this);
	}
	@Override
	public void visit(PathNode node) {
		PathNode[] children = node.getPaths();
		if(children==null) return;
		for(PathNode path : children) {
			int[] nodes = path.getPath();
			for(int n : nodes) {
				if(getContext().therePill(n)) {
					pillPaths.add(path);
					break;
				}
			}
			path.accept(this);
		}
	}
}
