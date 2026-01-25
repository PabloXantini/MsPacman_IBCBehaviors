package MsGrasa2026.pacman.rules;

import MsGrasa2026.common.behavior.BehaviorContext;
import MsGrasa2026.common.behavior.Rule;
import MsGrasa2026.pacman.Context;
import pacman.game.Constants.GHOST;

public class NoVulnerableGhosts extends Rule {
	@Override
	public boolean evaluate(BehaviorContext context) {
		Context ctx = (Context)context;
		GHOST[] ghosts = ctx.getVulnerableGhosts();
		if(ghosts.length == 0) return true;
		return false;
	}
}
