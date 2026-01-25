package MsGrasa2026.pacman;

import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;

import java.awt.Color;
import java.util.ArrayList;

import MsGrasa2026.common.behavior.BehaviorContext;
import MsGrasa2026.common.locations.Ghost;
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
	public boolean isGhostVulnerable(GHOST ghost) {
		return getGame().getGhostEdibleTime(ghost) > 15;
	}
	public GHOST[] getFreeGhosts() {
		ArrayList<GHOST> tmp = new ArrayList<GHOST>();
		for(GHOST ghost : GHOST.values()) {
			if(isGhostFree(ghost)) {
				tmp.add(ghost);
			}
		}
		GHOST[] freeGhosts = new GHOST[tmp.size()];
		return tmp.toArray(freeGhosts);
	}
	public GHOST[] getMenacingGhosts() {
		ArrayList<GHOST> tmp = new ArrayList<GHOST>();
		for(GHOST ghost : GHOST.values()) {
			if(isGhostFree(ghost) && !isGhostVulnerable(ghost)) {
				tmp.add(ghost);
			}
		}
		GHOST[] freeGhosts = new GHOST[tmp.size()];
		return tmp.toArray(freeGhosts);
	}
	public GHOST[] getVulnerableGhosts() {
		ArrayList<GHOST> tmp = new ArrayList<GHOST>();
		for(GHOST ghost : GHOST.values()) {
			if(isGhostVulnerable(ghost) && isGhostFree(ghost)) {
				tmp.add(ghost);
			}
		}
		GHOST[] vulnerableGhosts = new GHOST[tmp.size()];
		return tmp.toArray(vulnerableGhosts);
	}
	public Ghost getNearestGhostToPursue(int position, MOVE lastMove, GHOST[] ghosts, DM metric) {
		Ghost nearest = null;
		double shortestDistance = Double.MAX_VALUE;
		for(GHOST ghost : ghosts) {
			int ghostPosition = getGhostPosition(ghost);
			double ghostDistance = getGame().getDistance(position, ghostPosition, lastMove, metric);
			if(ghostDistance < shortestDistance) {
				shortestDistance = ghostDistance;
				nearest = new Ghost(ghost, ghostPosition);
				nearest.setDistance(shortestDistance, metric);
			}
		}
		if(nearest!=null) displayDistance(position, nearest.getPosition(), lastMove, getColor(), metric);
		return nearest;
	}
	public Ghost[] getNearGhostsByDistanceAway(int position, GHOST[] ghosts, DM metric, double distance) {
		ArrayList<Ghost> g = new ArrayList<Ghost>();
		int ghostc = 0;
		for(GHOST ghost : ghosts) {
			int ghostPosition = getGhostPosition(ghost);
			MOVE ghostMove = getGame().getGhostLastMoveMade(ghost);
			double ghostDistance = getGame().getDistance(ghostPosition, position, ghostMove, metric);
			if(ghostDistance < distance) {
				Ghost nearghost = new Ghost(ghost, ghostPosition);
				nearghost.setDistance(ghostDistance, metric);
				nearghost.setLastMove(ghostMove);
				g.add(nearghost);
				ghostc++;
			}
		}
		Ghost[] result = new Ghost[ghostc];
		return g.toArray(result);
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
