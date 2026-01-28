package MsGrasaTeam2026.common.behavior;

import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;

public class Behavior {
	private final String owner;
	private State initState;
	private State currState;
	public Behavior(String owner) {
		this.owner = owner;
	}
	public Behavior(GHOST ghost) {
		this.owner = ghost.name();
	}
	public String getOwner() {
		return owner;
	}
	public State getInitialState() {
		return initState;
	}
	public State getCurrentState() {
		return currState;
	}
	public void start(State state) {
		this.initState = state;
		this.currState = this.initState;
	}
	public void reset() {
		this.currState = this.initState;
	}
	public void addTransition(State fromState, Rule rule, State toState) {
		fromState.addStateChange(rule, toState);
	}
	public void addStrategy(SimpleState state, Rule rule, Action action) {
		state.addStrategy(rule, action);
	}
	public MOVE run(BehaviorContext context) {
		context.compute();
		State nextState = currState.hasChanged(context);
		if(nextState!=null) {
			currState = nextState;
			if(context.getDebug()) currState.notify(context, currState.getName()+" enabled.");
		}  
		return currState.execute(context);
	}
}
