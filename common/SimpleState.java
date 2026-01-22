package MsGrasa2026.common;

import java.util.LinkedHashMap;

import pacman.game.Constants.MOVE;

public class SimpleState extends State {
	private final Action defaultAction;
	private LinkedHashMap<Rule, Action> strategies = new LinkedHashMap<Rule, Action>();
	public SimpleState(String name, Action defaultAction) {
		super(name);
		this.defaultAction = defaultAction;
	}
	@Override
	public MOVE execute(BehaviorContext context) {
		for(Rule rule : strategies.keySet()) {
			if(rule.evaluate(context)) return strategies.get(rule).apply(context);
		}
		return defaultAction.apply(context);
	}
	public void addStrategy(Rule ruleToBeApplied, Action actionPerformed) {
		strategies.put(ruleToBeApplied, actionPerformed);
	}

}
