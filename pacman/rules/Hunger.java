package MsGrasa2026.pacman.rules;

import MsGrasa2026.common.behavior.BehaviorContext;
import MsGrasa2026.common.behavior.Rule;
import MsGrasa2026.pacman.Context;

public class Hunger extends Rule{
	private int hungerTicks = 0;
	private int last = 0;
	private final int reward;
	private final int threshold;
	public Hunger(int threshold, int reward) {
		this.threshold = threshold;
		this.reward = reward;
	}
	@Override
	public boolean evaluate(BehaviorContext context) {
		Context ctx = (Context)context;
		if(!ctx.pillEaten()) hungerTicks++;
		if(hungerTicks > threshold) {
			last = hungerTicks;
			hungerTicks-=reward;
			return true;
		}
		return false;
	}
	@Override
	public String toString() {
		return "MsPacman hungry("+last+")";
	}
}
