package MsGrasa2026.common;

import pacman.game.Game;

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
	public abstract void compute();
}
