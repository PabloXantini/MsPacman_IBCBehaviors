package MsGrasa2026.common.path;

import MsGrasa2026.common.behavior.BehaviorContext;

public abstract class GeneralPathVisitor implements PathVisitor {
	private final BehaviorContext context;
	public GeneralPathVisitor(BehaviorContext context) {
		this.context = context;
	}
	public BehaviorContext getContext() {
		return context;
	}
}
