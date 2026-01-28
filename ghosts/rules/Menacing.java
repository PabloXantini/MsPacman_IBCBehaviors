package MsGrasaTeam2026.ghosts.rules;

import MsGrasaTeam2026.common.behavior.BehaviorContext;
import pacman.game.Constants.GHOST;

public class Menacing extends GhostRule {
	public Menacing(GHOST ghost) {
		super(ghost);
	}
	@Override
	public boolean evaluate(BehaviorContext context) {
		return context.isGhostFree(ghost) || context.getGame().getGhostEdibleTime(ghost) <= 0;
	}

}
