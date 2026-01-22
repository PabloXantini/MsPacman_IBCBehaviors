package MsGrasa2026;

import java.util.EnumMap;

import pacman.controllers.GhostController;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class Es_MsGrasa_2026_ghosts_v1 extends GhostController {
	private EnumMap<GHOST, GhostState> ghostStates = new EnumMap<GHOST, GhostState>(GHOST.class);
	private EnumMap<GHOST, MOVE> commandMoves = new EnumMap<GHOST, MOVE>(GHOST.class);
	//MOD FLAGS
	private boolean DEBUG = true;
	public Es_MsGrasa_2026_ghosts_v1() {
		
	}
	@Override
	public EnumMap<GHOST, MOVE> getMove(Game game, long timeDue) {
		for (GHOST ghost : GHOST.values()) {
			if(game.doesGhostRequireAction(ghost) && !ghostStates.isEmpty()) {
				ghostStates.get(ghost).check(game);
				MOVE ghostMove = ghostStates.get(ghost).execute(game);
				commandMoves.put(ghost, ghostMove);
			}
		}
		return commandMoves;
	}
	public void changeState(GHOST ghost, GhostState ghostState) {
		ghostStates.put(ghost, ghostState);
		if(DEBUG) ghostState.notifyState();
	}
}

abstract class GhostState {
	private GHOST owner;
	Es_MsGrasa_2026_ghosts_v1 context;
	public GhostState(Es_MsGrasa_2026_ghosts_v1 context) {
		this.context = context;
	}
	public Es_MsGrasa_2026_ghosts_v1 getContext() {
		return context;
	}
	public GHOST getOwner() {
		return owner;
	}
	public abstract void check(Game game);
	public abstract MOVE execute(Game game);
	public abstract void notifyState();
}

abstract class GhostRule {
	Es_MsGrasa_2026_ghosts_v1 context;
	public GhostRule(Es_MsGrasa_2026_ghosts_v1 context) {
		this.context = context;
	}
	public Es_MsGrasa_2026_ghosts_v1 getContext() {
		return context;
	}
	public abstract boolean evaluate(Game game);
}

abstract class GhostAction {
	Es_MsGrasa_2026_ghosts_v1 context;
	public GhostAction(Es_MsGrasa_2026_ghosts_v1 context) {
		this.context = context;
	}
	public Es_MsGrasa_2026_ghosts_v1 getContext() {
		return context;
	}
	public abstract MOVE apply(Game game);
}

//BLINKY ACTIONS
class NearChase extends GhostAction {
	public NearChase(Es_MsGrasa_2026_ghosts_v1 context) {
		super(context);
	}
	@Override
	public MOVE apply(Game game) {
		int pacmanPosition = game.getPacmanCurrentNodeIndex();
		return null;
	}
	
}
//INKY ACTIONS

//PINKY ACTIONS

//SUE ACTIONS

class Chase extends GhostState {
	public Chase(Es_MsGrasa_2026_ghosts_v1 context) {
		super(context);
	}

	@Override
	public void check(Game game) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public MOVE execute(Game game) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void notifyState() {
		System.out.println(getOwner().name()+": CHASE STATE ENABLED");	
	}
}