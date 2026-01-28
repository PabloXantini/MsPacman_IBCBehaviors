package MsGrasa2026;

import java.util.EnumMap;

import MsGrasa2026.common.behavior.Action;
import MsGrasa2026.common.behavior.Behavior;
import MsGrasa2026.common.behavior.IF;
import MsGrasa2026.common.behavior.Rule;
import MsGrasa2026.common.behavior.SimpleState;
import MsGrasa2026.ghosts.actions.ChasePacman;
import MsGrasa2026.pacman.Context;
import MsGrasa2026.pacman.actions.AvoidPowerPill;
import MsGrasa2026.pacman.actions.ChaseGhost;
import MsGrasa2026.pacman.actions.GoToPill;
import MsGrasa2026.pacman.actions.RunAway;
import MsGrasa2026.pacman.actions.TryEatPill;
import MsGrasa2026.pacman.rules.AwayFromGhosts;
import MsGrasa2026.pacman.rules.Eaten;
import MsGrasa2026.pacman.rules.Hunger;
import MsGrasa2026.pacman.rules.NearToGhosts;
import MsGrasa2026.pacman.rules.NoVulnerableGhosts;
import MsGrasa2026.pacman.rules.PowerPillEaten;
import MsGrasa2026.pacman.rules.SoCloseOfPowerPill;
import pacman.controllers.GhostController;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class Es_MsGrasa_2026_ghosts extends GhostController {
	private EnumMap<GHOST, Behavior> ghostsAI = new EnumMap<GHOST, Behavior>(GHOST.class);
	//MOD FLAGS
	private enum MODE{
		CUSTOM,
		GENERIC
	}
	private MODE mode = MODE.GENERIC;
	private boolean DEBUG_MODE = false;
	public Es_MsGrasa_2026_ghosts() {
		switch (mode) {
		case GENERIC:
			setupGeneric();
			break;
		case CUSTOM:
			setupCustom();
			break;
		default:
			break;
		}
	}
	private void setupGeneric() {
		for(GHOST ghost : GHOST.values()) {
			Behavior ghostAI = new Behavior(ghost);
			
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
			Action chase = new ChasePacman(ghost);
			Action tryEat = new TryEatPill();
			Action gotoPill = new GoToPill();
			Action avoidPPill = new AvoidPowerPill();
			Action runAway = new RunAway(45.0d);
			//Action chase = new ChaseGhost();
			//STATES
			SimpleState pursuit = new SimpleState("Pursuit", chase);
			///this.aware = new SimpleState("Aware", tryEat);
			///this.pursuit = new SimpleState("Pursuit", tryEat);
			
			//SETUP
			///this.aware.setInferenceMode(IF.FUZZY);
			///this.pursuit.setInferenceMode(IF.FUZZY);
			//->CHASE
			///ghostAI.addTransition(chase, nGhosts1, aware);
			///this.ai.addStrategy(greedy, nPPill1, avoidPPill);
			///this.ai.addStrategy(greedy, hunger1, gotoPill);
			//->AWARE
			///this.ai.addTransition(aware, pcEaten, greedy);
			///this.ai.addTransition(aware, ppEaten, pursuit);
			///this.ai.addTransition(aware, aGhosts1, greedy);
			///this.ai.addStrategy(aware, nGhosts2, runAway);
			///this.ai.addStrategy(aware, aGhosts2, gotoPill);
			//->PURSUIT
			///this.ai.addTransition(pursuit, pcEaten, greedy);
			///this.ai.addTransition(pursuit, noVGhosts, aware);
			///this.ai.addStrategy(pursuit, nGhosts3, chase);
			///this.ai.addStrategy(pursuit, nGhosts4, runAway);
			///this.ai.addStrategy(pursuit, nPPill1, avoidPPill);
			//FINALLY
			ghostAI.start(pursuit);
			
			//PUT
			ghostsAI.put(ghost, ghostAI);
		}
	}
	private void setupCustom() {
		
	}
	@Override
	public EnumMap<GHOST, MOVE> getMove(Game game, long timeDue) {
		Context ctx = new Context(game);
		ctx.setDebug(DEBUG_MODE);
		EnumMap<GHOST, MOVE> commandMoves = new EnumMap<GHOST, MOVE>(GHOST.class);
		for(GHOST ghost : GHOST.values()) {
			Behavior ai = ghostsAI.get(ghost);
			MOVE move = ai.run(ctx);
			commandMoves.put(ghost, move);
		}
		return commandMoves;
	}
	public Es_MsGrasa_2026_ghosts enableDebug(boolean flag) {
		DEBUG_MODE = flag;
		return this;
	}
}
