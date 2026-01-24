package MsGrasa2026.pacman;

import pacman.game.Constants.MOVE;

import java.awt.Color;

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
	public MOVE[] getPossibleMoves() {
		return getGame().getPossibleMoves(position, lastMove);
	}
	@Override
	public void compute() {
		this.position = getGame().getPacmanCurrentNodeIndex();
		this.lastMove = getGame().getPacmanLastMoveMade();
	}
}
