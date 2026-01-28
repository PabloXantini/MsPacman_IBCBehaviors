package MsGrasaTeam2026;

import java.util.EnumMap;

import MsGrasaTeam2026.common.behavior.Action;
import MsGrasaTeam2026.common.behavior.Behavior;
import MsGrasaTeam2026.common.behavior.IF;
import MsGrasaTeam2026.common.behavior.Rule;
import MsGrasaTeam2026.common.behavior.SimpleState;
import MsGrasaTeam2026.ghosts.actions.BlockingChase;
import MsGrasaTeam2026.ghosts.actions.NervousChase;
import MsGrasaTeam2026.ghosts.actions.RunAway;
import MsGrasaTeam2026.ghosts.actions.SimpleChasePacman;
import MsGrasaTeam2026.ghosts.actions.StayedChase;
import MsGrasaTeam2026.ghosts.rules.Menacing;
import MsGrasaTeam2026.pacman.Context;
import MsGrasaTeam2026.pacman.rules.Eaten;
import MsGrasaTeam2026.pacman.rules.SoCloseOfPowerPill;
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
			Rule nPPill = new SoCloseOfPowerPill(30.0d);
			Rule pcEaten = new Eaten();
			Rule menacing = new Menacing(ghost);
			//ACTIONS
			Action chase = null;
			Action runAway = new RunAway(ghost);
			//STATES
			switch(ghost) {
				case BLINKY: 
					chase = new SimpleChasePacman(ghost);
					break;
				case INKY:
					chase = new StayedChase(ghost);
					break;
				case PINKY:
					chase = new BlockingChase(ghost);
					break;
				case SUE:
					chase = new NervousChase(ghost, 30);
					break;
			}
			SimpleState pursuit = new SimpleState("Pursuit", chase);
			SimpleState scared = new SimpleState("Scared", runAway);
			///this.aware = new SimpleState("Aware", tryEat);
			///this.pursuit = new SimpleState("Pursuit", tryEat);
			
			//SETUP
			//->CHASE
			ghostAI.addTransition(pursuit, nPPill, scared);
			//->SCARED
			ghostAI.addTransition(scared, pcEaten, pursuit);
			ghostAI.addTransition(scared, menacing, pursuit);
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
