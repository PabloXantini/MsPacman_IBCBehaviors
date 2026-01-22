package MsGrasa2026.common;

import java.util.LinkedHashMap;

import pacman.game.Constants.MOVE;

public abstract class State {
	private final String name;
	private boolean active = true;
	private LinkedHashMap<Rule, State> transitions = new LinkedHashMap<Rule, State>();
	public State(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public boolean isActive() {
		return active;
	}
	public LinkedHashMap<Rule, State> getTransitions(){
		return transitions;
	}
	public State hasChanged(BehaviorContext context) {
		for(Rule rule : transitions.keySet()) {
			if(rule.evaluate(context)) return transitions.get(rule);
		}
		return null;
	}	
	public void notify(BehaviorContext context, String message) {
		if(context.getDebug()) System.out.println(getName()+": "+message);
	}
	public void addStateChange(Rule rule, State state) {
		transitions.put(rule, state);
	}
	public abstract MOVE execute(BehaviorContext context);
}
