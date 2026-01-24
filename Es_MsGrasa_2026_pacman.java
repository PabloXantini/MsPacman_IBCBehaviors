package MsGrasa2026;

import MsGrasa2026.common.behavior.Action;
import MsGrasa2026.common.behavior.Behavior;
import MsGrasa2026.common.behavior.Rule;
import MsGrasa2026.common.behavior.SimpleState;
import MsGrasa2026.pacman.Context;
import MsGrasa2026.pacman.actions.AvoidPowerPill;
import MsGrasa2026.pacman.actions.RandomMove;
import MsGrasa2026.pacman.actions.TryEatPill;
import MsGrasa2026.pacman.rules.NearToGhosts;
import MsGrasa2026.pacman.rules.SoCloseOfPowerPill;
import pacman.controllers.PacmanController;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class Es_MsGrasa_2026_pacman extends PacmanController{
	private static final boolean DEBUG_MODE = true; 
	private Behavior ai;
	private SimpleState greedy;
	private SimpleState aware;
	private SimpleState pursuit;
	public Es_MsGrasa_2026_pacman() {
		//FSM
		this.ai = new Behavior("MsPacman");
		
		//RULES
		Rule nPPill1 = new SoCloseOfPowerPill(30.0d);
		Rule nGhosts = new NearToGhosts(45.0d);
		
		//ACTIONS
		Action random = new RandomMove();
		Action tryEat = new TryEatPill();
		Action avoidPPill = new AvoidPowerPill();
		
		//STATES
		this.greedy = new SimpleState("Greedy", tryEat);
		this.aware = new SimpleState("Aware", random);
		this.pursuit = new SimpleState("Pursuit", random);
		
		//SETUP
		//->GREEDY
		this.ai.addTransition(greedy, nGhosts, aware);
		this.ai.addStrategy(greedy, nPPill1, avoidPPill);
		//->AWARE
		
		//->PURSUIT
		
		//FINALLY
		this.ai.start(greedy);
	}
	@Override
	public MOVE getMove(Game game, long timeDue) {
		Context ctx = new Context(game);
		ctx.setDebug(DEBUG_MODE);
		return this.ai.run(ctx);
	}
}
