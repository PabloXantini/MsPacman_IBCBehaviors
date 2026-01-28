package MsGrasaTeam2026.ghosts.rules;

import MsGrasaTeam2026.common.behavior.Rule;
import pacman.game.Constants.GHOST;

public abstract class GhostRule extends Rule {
	protected final GHOST ghost;
	public GhostRule(GHOST ghost) {
		this.ghost = ghost;
	}
}
