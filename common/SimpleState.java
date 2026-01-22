package MsGrasa2026.common;

import java.util.LinkedHashMap;

import pacman.game.Constants.MOVE;

public class SimpleState extends State {
	private final Action defaultAction;
	private LinkedHashMap<Rule, Action> strategies = new LinkedHashMap<Rule, Action>();
	private IF inferenceMode = IF.RULE; 
	public SimpleState(String name, Action defaultAction) {
		super(name);
		this.defaultAction = defaultAction;
	}
	@Override
	public MOVE execute(BehaviorContext context) {
		switch (inferenceMode) {
		case RULE: return inferRule(context);
		default: return MOVE.NEUTRAL;
		}
	}
	public void setInferenceMode(IF mode) {
		this.inferenceMode = mode;
	}
	public void addStrategy(Rule ruleToBeApplied, Action actionPerformed) {
		strategies.put(ruleToBeApplied, actionPerformed);
	}
	private MOVE inferRule(BehaviorContext context) {
		for(Rule rule : strategies.keySet()) {
			if(rule.evaluate(context)) {
				Action action = strategies.get(rule);
				rule.notify(context, getName()+"-> Strategy: "+rule.toString()+" Action: "+action.toString());
				return strategies.get(rule).apply(context);
			}
		}
		defaultAction.notify(context, getName()+"-> Action: "+defaultAction.toString());
		return defaultAction.apply(context);
	}
}
