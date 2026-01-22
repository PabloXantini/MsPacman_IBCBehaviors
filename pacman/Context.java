package MsGrasa2026.pacman;

import MsGrasa2026.common.BehaviorContext;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class Context extends BehaviorContext{
	private MOVE lastMove;
	private int position;
	public Context(Game game) {
		super(game);
	}
	public int getPosition() {
		return position;
	}
	public MOVE getLastMove() {
		return lastMove;
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
