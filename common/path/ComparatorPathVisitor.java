package MsGrasa2026.common.path;

import MsGrasa2026.common.behavior.BehaviorContext;

public abstract class ComparatorPathVisitor extends GeneralPathVisitor {
	public ComparatorPathVisitor(BehaviorContext context) {
		super(context);
	}
	public abstract void compare(PathNode node1, PathNode node2);
}
