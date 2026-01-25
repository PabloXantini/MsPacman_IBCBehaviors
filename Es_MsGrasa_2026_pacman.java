package MsGrasa2026;

import MsGrasa2026.common.behavior.Action;
import MsGrasa2026.common.behavior.Behavior;
import MsGrasa2026.common.behavior.IF;
import MsGrasa2026.common.behavior.Rule;
import MsGrasa2026.common.behavior.SimpleState;
import MsGrasa2026.pacman.Context;
import MsGrasa2026.pacman.actions.AvoidPowerPill;
import MsGrasa2026.pacman.actions.ChaseGhost;
import MsGrasa2026.pacman.actions.RandomMove;
import MsGrasa2026.pacman.actions.RunAway;
import MsGrasa2026.pacman.actions.TryEatPill;
import MsGrasa2026.pacman.rules.AwayFromGhosts;
import MsGrasa2026.pacman.rules.Eaten;
import MsGrasa2026.pacman.rules.NearToGhosts;
import MsGrasa2026.pacman.rules.NoVulnerableGhosts;
import MsGrasa2026.pacman.rules.PowerPillEaten;
import MsGrasa2026.pacman.rules.SoCloseOfPowerPill;
import pacman.controllers.PacmanController;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class Es_MsGrasa_2026_pacman extends PacmanController{
	private static final boolean DEBUG_MODE = false; 
	private Behavior ai;
	private SimpleState greedy;
	private SimpleState aware;
	private SimpleState pursuit;
	public Es_MsGrasa_2026_pacman() {
		//FSM
		this.ai = new Behavior("MsPacman");
		
		//RULES
		Rule nPPill1 = new SoCloseOfPowerPill(30.0d);
		Rule aGhosts1 = new AwayFromGhosts(65.0d);
		Rule nGhosts1 = new NearToGhosts(65.0d);
		Rule nGhosts2 = new NearToGhosts(100.0d);
		Rule nGhosts3 = new NearToGhosts(80.0d); 
		Rule nGhosts4 = new NearToGhosts(30.0d);
		Rule pEaten = new Eaten();
		Rule ppEaten = new PowerPillEaten();
		Rule novghosts = new NoVulnerableGhosts();
		//ACTIONS
		Action tryEat = new TryEatPill();
		Action avoidPPill = new AvoidPowerPill();
		Action runAway = new RunAway(45.0d);
		Action chase = new ChaseGhost();
		//STATES
		this.greedy = new SimpleState("Greedy", tryEat);
		this.aware = new SimpleState("Aware", tryEat);
		this.pursuit = new SimpleState("Pursuit", tryEat);
		
		//SETUP
		aware.setInferenceMode(IF.FUZZY);
		pursuit.setInferenceMode(IF.FUZZY);
		//->GREEDY
		this.ai.addTransition(greedy, nGhosts1, aware);
		this.ai.addStrategy(greedy, nPPill1, avoidPPill);
		//->AWARE
		this.ai.addTransition(aware, pEaten, greedy);
		this.ai.addTransition(aware, ppEaten, pursuit);
		this.ai.addTransition(aware, aGhosts1, greedy);
		this.ai.addStrategy(aware, nGhosts2, runAway);
		//->PURSUIT
		this.ai.addTransition(pursuit, pEaten, greedy);
		this.ai.addTransition(pursuit, novghosts, aware);
		this.ai.addStrategy(pursuit, nGhosts3, chase);
		this.ai.addStrategy(pursuit, nGhosts4, runAway);
		this.ai.addStrategy(pursuit, nPPill1, avoidPPill);
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
