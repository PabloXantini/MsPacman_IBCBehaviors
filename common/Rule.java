package MsGrasa2026.common;

public abstract class Rule {
	public Rule() {
	}
	public void notify(BehaviorContext context, String message) {
		if(context.getDebug()) System.out.println(message);
	}
	public abstract boolean evaluate(BehaviorContext context);
}
