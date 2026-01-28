package MsGrasaTeam2026;

import MsGrasaTeam2026.common.behavior.Action;
import MsGrasaTeam2026.common.behavior.Behavior;
import MsGrasaTeam2026.common.behavior.IF;
import MsGrasaTeam2026.common.behavior.Rule;
import MsGrasaTeam2026.common.behavior.SimpleState;
import MsGrasaTeam2026.pacman.Context;
import MsGrasaTeam2026.pacman.actions.AvoidPowerPill;
import MsGrasaTeam2026.pacman.actions.ChaseGhost;
import MsGrasaTeam2026.pacman.actions.GoToPill;
import MsGrasaTeam2026.pacman.actions.RandomMove;
import MsGrasaTeam2026.pacman.actions.RunAway;
import MsGrasaTeam2026.pacman.actions.TryEatPill;
import MsGrasaTeam2026.pacman.rules.AwayFromGhosts;
import MsGrasaTeam2026.pacman.rules.Eaten;
import MsGrasaTeam2026.pacman.rules.Hunger;
import MsGrasaTeam2026.pacman.rules.NearToGhosts;
import MsGrasaTeam2026.pacman.rules.NoVulnerableGhosts;
import MsGrasaTeam2026.pacman.rules.PowerPillEaten;
import MsGrasaTeam2026.pacman.rules.SoCloseOfPowerPill;
import pacman.controllers.PacmanController;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class Es_MsGrasa_2026_pacman extends PacmanController{
	private boolean DEBUG_MODE = false; 
	private Behavior ai;
	private SimpleState greedy;
	private SimpleState aware;
	private SimpleState pursuit;
	public Es_MsGrasa_2026_pacman() {
		//FSM
		this.ai = new Behavior("MsPacman");
		
		//RULES
		Rule nPPill1 = new SoCloseOfPowerPill(30.0d);
		Rule aGhosts1 = new AwayFromGhosts(60.0d);
		
		Rule aGhosts2 = new AwayFromGhosts(60.0d);
		Rule nGhosts1 = new NearToGhosts(60.0d);
		
		Rule nGhosts2 = new NearToGhosts(100.0d);
		Rule nGhosts3 = new NearToGhosts(100.0d); 
		Rule nGhosts4 = new NearToGhosts(80.0d);
		
		Rule pcEaten = new Eaten();
		Rule ppEaten = new PowerPillEaten();
		Rule noVGhosts = new NoVulnerableGhosts();
		
		Rule hunger1 = new Hunger(30, 3);
		//ACTIONS
		Action tryEat = new TryEatPill();
		Action gotoPill = new GoToPill();
		Action avoidPPill = new AvoidPowerPill();
		Action runAway = new RunAway(45.0d);
		Action chase = new ChaseGhost();
		//STATES
		this.greedy = new SimpleState("Greedy", tryEat);
		this.aware = new SimpleState("Aware", tryEat);
		this.pursuit = new SimpleState("Pursuit", tryEat);
		
		//SETUP
		this.aware.setInferenceMode(IF.FUZZY);
		this.pursuit.setInferenceMode(IF.FUZZY);
		//->GREEDY
		this.ai.addTransition(greedy, nGhosts1, aware);
		this.ai.addStrategy(greedy, nPPill1, avoidPPill);
		this.ai.addStrategy(greedy, hunger1, gotoPill);
		//->AWARE
		this.ai.addTransition(aware, pcEaten, greedy);
		this.ai.addTransition(aware, ppEaten, pursuit);
		this.ai.addTransition(aware, aGhosts1, greedy);
		this.ai.addStrategy(aware, nGhosts2, runAway);
		this.ai.addStrategy(aware, aGhosts2, gotoPill);
		//->PURSUIT
		this.ai.addTransition(pursuit, pcEaten, greedy);
		this.ai.addTransition(pursuit, noVGhosts, aware);
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
	public Es_MsGrasa_2026_pacman enableDebug(boolean flag) {
		this.DEBUG_MODE = flag;
		return this;
	}
}
