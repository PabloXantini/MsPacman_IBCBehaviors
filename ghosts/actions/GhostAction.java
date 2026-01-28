package MsGrasa2026.ghosts.actions;

import MsGrasa2026.common.behavior.Action;
import pacman.game.Constants.GHOST;

public abstract class GhostAction extends Action {
	protected final GHOST ghost;
	public GhostAction(GHOST ghost) {
		this.ghost = ghost;
	}
}
