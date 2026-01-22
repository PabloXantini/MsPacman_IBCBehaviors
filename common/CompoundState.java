package MsGrasa2026.common;

import pacman.game.Constants.MOVE;

public class CompoundState extends State{
	private Behavior subBehavior;
	public CompoundState(String name, Behavior subBehavior) {
		super(name);
		this.subBehavior = subBehavior;
	}
	@Override
	public MOVE execute(BehaviorContext context) {
		return subBehavior.run(context);
	}
}
