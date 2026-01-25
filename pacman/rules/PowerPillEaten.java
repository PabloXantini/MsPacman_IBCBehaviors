package MsGrasa2026.pacman.rules;

import MsGrasa2026.common.behavior.BehaviorContext;
import MsGrasa2026.common.behavior.Rule;

public class PowerPillEaten extends Rule{
	@Override
	public boolean evaluate(BehaviorContext context) {
		return context.powerPillEaten();
	}
	@Override
	public String toString() {
		return "Eaten a PowerPill:"+hashCode();
	}
}
