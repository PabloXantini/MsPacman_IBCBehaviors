package MsGrasa2026.common.behavior;

import java.awt.Color;
import java.util.ArrayList;

import MsGrasa2026.common.locations.Ghost;
import MsGrasa2026.common.locations.Pill;
import MsGrasa2026.common.path.PathNode;
import pacman.game.Game;
import pacman.game.GameView;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;

public abstract class BehaviorContext {
	private final Game gameContext;
	private boolean debug = false;
	public BehaviorContext(Game game) {
		this.gameContext = game;
	}
	public Game getGame() {
		return gameContext;
	}
	public boolean getDebug() {
		return debug;
	}
	public void setDebug(boolean flag) {
		this.debug = flag;
	}
	//POSITION METHODS
	public boolean isInJunction(int position) {
		return gameContext.isJunction(position);
	}
	public int[] getNeigbours(int position) {
		return gameContext.getNeighbouringNodes(position);
	}
	//BOARD METHODS
	//POWERPILLS
	public int[] getPowerPills() {
		return gameContext.getActivePowerPillsIndices();
	}
	public Pill getNearestPowerPill(int position, MOVE lastMove, DM metric) {
		Pill nearest = null;
		double shortestDistance = Double.MAX_VALUE;
		for(int PPill : getPowerPills()) {
			double distance = gameContext.getDistance(position, PPill, metric);
			if(distance < shortestDistance) {
				shortestDistance = distance;
				nearest = new Pill(PPill);
				nearest.setDistance(shortestDistance, metric);
			}
		}
		displayDistance(position, nearest.getPosition(), Color.WHITE, metric);
		return nearest;
	}
	//PILLS
	public boolean therePill(int position) {
		int pillIndex = gameContext.getPillIndex(position);
		if(pillIndex == -1) return false;
		return gameContext.isPillStillAvailable(pillIndex);
	}
	//GHOST TRACK METHODS
	public int getGhostPosition(GHOST ghost) {
		return gameContext.getGhostCurrentNodeIndex(ghost);
	}
	public Ghost getNearestGhost(int position, MOVE lastMove, GHOST[] ghosts, DM metric) {
		Ghost nearest = null;
		double shortestDistance = Double.MAX_VALUE;
		for(GHOST ghost : ghosts) {
			int ghostPosition = getGhostPosition(ghost);
			double ghostDistance = gameContext.getDistance(position, ghostPosition, lastMove, metric);
			if(ghostDistance < shortestDistance) {
				shortestDistance = ghostDistance;
				nearest = new Ghost(ghost, ghostPosition);
			}
		}
		displayDistance(position, nearest.getPosition(), getGhostColor(nearest.getName()), metric);
		return nearest;
	}
	//PATH METHODS
	public int[] getShortestPath(int from, int to, MOVE lastMove) {
		return gameContext.getShortestPath(from, to, lastMove);
	}
	public int[] computePathFreedomDegrees(int[] path) {
		int[] weigthedPath = new int[path.length];
		for(int i = 0; i<path.length; i++) {
			int[] inodes = gameContext.getNeighbouringNodes(path[i]);
			weigthedPath[i] = inodes.length - 1; 
		}
		return weigthedPath;
	}
	public boolean thereAlternatives(int[] wpath) {
		for (int i : wpath) {
			if(i >= 2) {
				return true;
			}
		}
		return false;
	}
	public PathNode getPath(int position, MOVE lastMove, Color color) {
		PathNode node;
		ArrayList<Integer> tmp = new ArrayList<Integer>();
		MOVE moveMark = lastMove;
		int positionMark = position;
		while (!gameContext.isJunction(positionMark)) {
			if(debug) GameView.addPoints(gameContext, color, positionMark);			
			int nextMark = gameContext.getNeighbouringNodes(positionMark, moveMark)[0];
			tmp.add(positionMark);
			moveMark = gameContext.getMoveToMakeToReachDirectNeighbour(positionMark, nextMark);
			positionMark = nextMark;
		}
		int[] nodes = new int[tmp.size()];
		for (int i = 0; i < nodes.length; i++) {
			nodes[i] = tmp.get(i);
		}
		node = new PathNode(positionMark, lastMove, moveMark, nodes);
		return node;
	}
	public PathNode getPathTree(int position, MOVE lastMove, int depth, Color color) {
		PathNode node = new PathNode(position, lastMove);
		if(depth==0) return node;
		int[] neighbours = gameContext.getNeighbouringNodes(position, lastMove);
		MOVE[] moves = gameContext.getPossibleMoves(position, lastMove);
		//if(neighbours.length < 2) return node;
		PathNode[] children = new PathNode[neighbours.length];
		for (int i = 0; i < neighbours.length; i++) {
			PathNode next = getPath(neighbours[i], moves[i], color);
			children[i] = getPathTree(next.getOut(), next.getLastMove(), depth -1, color);
			children[i].setFirstMove(moves[i]);
			children[i].setPath(next.getPath());
		}
		node.addPaths(children);
		return node;
	}
	//MOVE METHODS
	public MOVE moveAwayFromTarget(int position, int target, MOVE lastMove, DM metric) {
		return gameContext.getNextMoveAwayFromTarget(position, target, lastMove, metric);
	}
	public MOVE moveTowardsTarget(int position, int target, MOVE lastMove, DM metric) {
		return gameContext.getNextMoveTowardsTarget(position, target, lastMove, metric);
	}
	//DRAW METHODS
	public Color getGhostColor(GHOST ghost) {
		switch (ghost) {
			case BLINKY: return Color.RED;
			case INKY: return Color.CYAN;
			case PINKY: return Color.PINK;
			case SUE: return Color.ORANGE;
			default: return Color.WHITE;
		}
	}
	public void displayDistance(int from, int to, Color color, DM metric) {
		if(debug) {
			switch (metric) {
			case EUCLID:
				GameView.addLines(gameContext, color, from, to);
				break;
			case PATH:
				int[] path = gameContext.getShortestPath(from, to);
				GameView.addPoints(gameContext, color, path);
				break;
			default:
				break;
			}
		}
	}
	public void addPoints(Color color, int... positions) {
		GameView.addPoints(gameContext, color, positions);
	}
	public void addLine(Color color, int from, int to) {
		GameView.addLines(gameContext, color, from, to);
	}
	public void addLines(Color color, int[] from, int[] to) {
		GameView.addLines(gameContext, color, from, to);
	}
	public abstract void compute();
}
