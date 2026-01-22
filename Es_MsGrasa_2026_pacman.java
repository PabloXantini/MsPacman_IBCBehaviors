package MsGrasa2026;

import MsGrasa2026.common.Behavior;
import MsGrasa2026.common.SimpleState;
import MsGrasa2026.common.Action;
import MsGrasa2026.pacman.Context;
import MsGrasa2026.pacman.actions.RandomMove;
import pacman.controllers.PacmanController;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class Es_MsGrasa_2026_pacman extends PacmanController{
	private Behavior ai;
	private SimpleState greedy;
	private SimpleState aware;
	private SimpleState pursuit;
	public Es_MsGrasa_2026_pacman() {
		//FSM
		this.ai = new Behavior("MsPacman");
		//ACTIONS
		Action random = new RandomMove();
		//STATES
		this.greedy = new SimpleState("Greedy", random);
		this.aware = new SimpleState("Aware", random);
		this.pursuit = new SimpleState("Pursuit", random);
		//TRANSITIONS
		
		//STRATEGIES
		
		//SETUP
		this.ai.start(greedy);
	}
	@Override
	public MOVE getMove(Game game, long timeDue) {
		Context ctx = new Context(game);
		ctx.setDebug(true);
		return this.ai.run(ctx);
	}
}
