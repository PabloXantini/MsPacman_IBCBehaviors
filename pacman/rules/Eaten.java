package MsGrasaTeam2026.pacman.rules;

import MsGrasaTeam2026.common.behavior.BehaviorContext;
import MsGrasaTeam2026.common.behavior.Rule;

public class Eaten extends Rule{
	@Override
	public boolean evaluate(BehaviorContext context) {
		return context.pacmanEaten();
	}
	@Override
	public String toString() {
		return "Pacman Eaten: "+hashCode();
	}
}
