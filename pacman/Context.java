package MsGrasa2026.pacman;

import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;

import java.awt.Color;
import java.util.ArrayList;

import MsGrasa2026.common.behavior.BehaviorContext;
import pacman.game.Game;

public class Context extends BehaviorContext{
	private MOVE lastMove;
	private int position;
	private Color color = new Color(255, 222, 33);
	public Context(Game game) {
		super(game);
	}
	public int getPosition() {
		return position;
	}
	public MOVE getLastMove() {
		return lastMove;
	}
	public Color getColor() {
		return color;
	}
	private GHOST[] toArray(ArrayList<GHOST> ghosts) {
		GHOST[] g = new GHOST[ghosts.size()];
		for (int i = 0; i < g.length; i++) {
			g[i] = ghosts.get(i);
		}
		return g;
	}
	public GHOST[] getFreeGhosts() {
		ArrayList<GHOST> tmp = new ArrayList<GHOST>();
		for(GHOST ghost : GHOST.values()) {
			if(isGhostFree(ghost)) {
				tmp.add(ghost);
			}
		}
		GHOST[] freeGhosts = toArray(tmp);
		return freeGhosts;
	}
	public GHOST[] getVulnerableGhosts() {
		ArrayList<GHOST> tmp = new ArrayList<GHOST>();
		for(GHOST ghost : GHOST.values()) {
			if(getGame().getGhostEdibleTime(ghost) > 30 && isGhostFree(ghost)) {
				tmp.add(ghost);
			}
		}
		GHOST[] vulnerableGhosts = toArray(tmp);
		return vulnerableGhosts;
	}
	public MOVE[] getPossibleMoves() {
		return getGame().getPossibleMoves(position, lastMove);
	}
	@Override
	public void compute() {
		this.position = getGame().getPacmanCurrentNodeIndex();
		this.lastMove = getGame().getPacmanLastMoveMade();
	}
}
