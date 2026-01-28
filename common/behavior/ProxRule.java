package MsGrasa2026.common.behavior;

public abstract class ProxRule extends Rule {
	private double threshold;
	public ProxRule(double threshold) {
		this.threshold = threshold;
	}
	public double getThreshold() {
		return threshold;
	}
	public void setThreshold(double newValue) {
		this.threshold = newValue;
	}
}
