package MsGrasa2026.common;

import pacman.game.Constants.MOVE;

public abstract class Action {
	public Action() {}
	public void notify(BehaviorContext context, String message) {
		if(context.getDebug()) System.out.println(message);
	}
	public abstract MOVE apply(BehaviorContext context);
}
