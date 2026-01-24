package MsGrasa2026.common.behavior;

import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

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
		case FUZZY: return inferFuzzy(context);
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
				return action.apply(context);
			}
		}
		defaultAction.notify(context, getName()+"-> Action: "+defaultAction.toString());
		return defaultAction.apply(context);
	}
	private MOVE inferFuzzy(BehaviorContext context) {
		EnumMap<MOVE, Double> moveWeights = new EnumMap<MOVE, Double>(MOVE.class);
		for(Rule rule : strategies.keySet()) {
			if(rule.evaluate(context)) {
				double w = rule.getWeight();
				Action action = strategies.get(rule);
				rule.notify(context, getName()+"-> Strategy: "+rule.toString()+" Action: "+action.toString()+" ("+w+")");
				MOVE move = action.apply(context);
				if(moveWeights.get(move)==null) moveWeights.put(move, 0.0d);
				double current = moveWeights.get(move);
				current+=w;
				moveWeights.put(move, current);
			}
		}
		//Defuzzification
		MOVE bestMove = null;
		double max = Double.MIN_VALUE;
		for(Entry<MOVE, Double> e : moveWeights.entrySet()) {
			double v = e.getValue();
			if(max > v) {
				max = v;
				bestMove = e.getKey();
			}
		}
		return bestMove;
	}
}
