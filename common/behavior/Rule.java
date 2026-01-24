package MsGrasa2026.common.behavior;

public abstract class Rule {
	private double weight = 1.0d;
	public double getWeight() {
		return weight;
	}
	public void setWeight(double w) {
		this.weight = w;
	}
	public Rule() {
	}
	public void notify(BehaviorContext context, String message) {
		if(context.getDebug()) System.out.println(message);
	}
	public abstract boolean evaluate(BehaviorContext context);
}
