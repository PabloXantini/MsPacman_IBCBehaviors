package MsGrasa2026.common.path;

import pacman.game.Constants.MOVE;

public class PathNode {
	private MOVE firstMove;
	private MOVE lastMove;
	private int nodeOut;
	private int[] nodes;
	private double score;
	private PathNode[] paths;
	public PathNode(int nodeOut, MOVE lastMove) {
		this.nodeOut = nodeOut;
		this.lastMove = lastMove;
	}
	public PathNode(int nodeOut, MOVE firstMove, MOVE lastMove, int[] path) {
		this.nodeOut = nodeOut;
		this.firstMove = firstMove;
		this.lastMove = lastMove;
		this.nodes = path;
	}
	public MOVE getFirstMove() {
		return firstMove;
	}
	public MOVE getLastMove() {
		return lastMove;
	}
	public int getOut() {
		return nodeOut;
	}
	public int[] getPath() {
		return nodes;
	}
	public int getSteps() {
		return nodes==null ? 0 : nodes.length;
	}
	public double getScore() {
		return score;
	}
	public PathNode[] getPaths() {
		return paths;
	}
	public void setFirstMove(MOVE move) {
		this.firstMove = move;
	}
	public void setPath(int[] path) {
		this.nodes = path;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public void addPaths(PathNode[] paths) {
		this.paths = paths;
	}
	public void accept(PathVisitor visitor) {
		visitor.visit(this);
	}
}
